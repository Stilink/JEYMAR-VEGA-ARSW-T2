package edu.eci.arsw.coronavirus.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.eci.arsw.coronavirus.exception.APIException;
import edu.eci.arsw.coronavirus.exception.CountryNotFound;
import edu.eci.arsw.coronavirus.model.Ciudad;
import edu.eci.arsw.coronavirus.model.Pais;
import edu.eci.arsw.coronavirus.model.Provincia;
import edu.eci.arsw.coronavirus.services.APIRestConnector;

/**
 * CoronavirusCacheImpl
 */
@Component
public class CoronavirusCacheImpl implements CoronavirusCache {

    @Autowired
    private APIRestConnector connector;

    private final Map<String, Pais> paises = new HashMap<>();
    private Date lastModified;

    @Override
    public Set<Pais> getAllCountries() {
        Set<Pais> result = new HashSet<>();
        if (!cacheVigente()) { 
            paises.clear();
            JSONArray json = null;
            try {
                json = connector.getCountries();
            } catch (APIException e) {
                e.printStackTrace();
            }
            Set<Pais> countries = mapJsonToSet(json);
            fillMap(countries);
        }
        result.addAll(paises.values());
        return result;
    }

    @Override
    public Pais getCountryByName(String name) throws CountryNotFound{
        if (cacheVigente()) {
            if(!paises.containsKey(name)) throw new CountryNotFound("Pais no encontrado");
            return paises.get(name);
        }
        JSONArray json = null;
        try {
            json = connector.getCountryByName(name);
        } catch (APIException e) {
            e.printStackTrace();
            throw new CountryNotFound("Pais no encontrado");
        }
        if(json==null || json.size()==0) throw new CountryNotFound("Pais no encontrado");
        Set<Pais> result = mapJsonToSet(json);
        List<Pais> resultToList = new ArrayList<>();
        resultToList.addAll(result);
        Pais country = resultToList.get(0);
        return country;
    }

    private Set<Pais> mapJsonToSet(JSONArray countriesJSON) {
        Map<String, Pais> countries = new HashMap<>();
        Pais pais = null;
        Provincia provincia = null;
        for (int i = 0; i < countriesJSON.size(); i++) {
            JSONObject city = (JSONObject) countriesJSON.get(i);
            Ciudad ciudad = mapCiudad(city);
            pais = countries.getOrDefault(ciudad.getPais(), new Pais(ciudad.getPais(), new HashMap<>(), 0, 0, 0));
            provincia = pais.getProvincias().getOrDefault(ciudad.getProvincia(),
                    new Provincia(ciudad.getProvincia(), new HashMap<>(), 0, 0, 0));
            provincia.getCiudades().put(ciudad.getNombre(), ciudad);
            provincia.increaseCurados(ciudad.getCurados());
            provincia.increaseInfectados(ciudad.getInfectados());
            provincia.increaseMuertos(ciudad.getMuertos());
            pais.increaseCurados(ciudad.getCurados());
            pais.increaseInfectados(ciudad.getInfectados());
            pais.increaseMuertos(ciudad.getMuertos());
            pais.getProvincias().put(provincia.getNombre(), provincia);
            countries.put(pais.getNombre(), pais);
        }
        Set<Pais> paises = new HashSet<>();
        paises.addAll(countries.values());
        return paises;
    }

    private Ciudad mapCiudad(JSONObject cityJSON) {
        String nombre = (String) cityJSON.get("city");
        String provincia = (String) cityJSON.get("province");
        String pais = (String) cityJSON.get("country");
        long infectados = (long) cityJSON.get("confirmed");
        long muertos = (long) cityJSON.get("deaths");
        long curados = (long) cityJSON.get("recovered");
        Ciudad ciudad = new Ciudad(nombre, muertos, infectados, curados, pais, provincia);
        return ciudad;
    }

    private void fillMap(Set<Pais> paises) {
        for (Pais pais : paises) {
            this.paises.put(pais.getNombre(), pais);
        }
    }

    private boolean cacheVigente() {
        long confirmador = System.currentTimeMillis()-lastModified.getTime();
        if(confirmador>=300000){
            lastModified = new Date(System.currentTimeMillis());
            return false;
        }
        return true;
    }



    @PostConstruct
    private void postConstruct() {
        lastModified = new Date(System.currentTimeMillis()-310000);
        getAllCountries();
    }
}