package edu.eci.arsw.coronavirus.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import edu.eci.arsw.coronavirus.services.APIMapInformation;
import edu.eci.arsw.coronavirus.services.APIRestConnector;

/**
 * CoronavirusCacheImpl
 */
@Component
public class CoronavirusCacheImpl implements CoronavirusCache {

    /**
     * Api relacionada, la cual originalmente es la api del parcial.
     */
    @Autowired
    private APIRestConnector connector;

    /**
     * Api relacionada para el consumo de latitudes y longitudes.
     */
    @Autowired
    private APIMapInformation mapConnector;

    private final Map<String, Pais> paises = new HashMap<>();
    private Date lastModified;
    // Parametro que representa el numero de minutos en milisegundos para el control del cache.
    private long minutosEnMilisegundos = 300000;

    /**
     * Solicita a la api relacionada la información de todos los paises
     * @return Conjunto con toda la información
     */
    @Override
    public Set<Pais> getAllCountries() {
        Set<Pais> result = new HashSet<>();
        if (!cacheVigente()) { 
            System.out.println("PETICION A LA API - "+new Date(System.currentTimeMillis()));
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

    /**
     * Solicita a la api relacionada la información de todos los paises
     * @return Conjunto con toda la información
     */
    @Override
    public Pais getCountryByName(String name) throws CountryNotFound{
        if (cacheVigente()) {
            if(!paises.containsKey(name)) throw new CountryNotFound("Pais no encontrado");
            return paises.get(name);
        }else{
            if(!paises.containsKey(name)) throw new CountryNotFound("Pais no encontrado");
            getAllCountries();
            return paises.get(name);
        }
        
    }

    /**
     * Solicita la información relacionada a la ubicación.
     * @param country Nombre del país solicitado
     * @return Vector de tamaño 2 que representa la latitud y la longitud del pais
     * @throws APIException En caso de fallo de la api.
     * @throws CountryNotFound En caso de no encontrar el país solicitado.
     */
    @Override
    public double[] getMapInfoByCountry(String name) throws APIException, CountryNotFound{
        if(!paises.containsKey(name)) throw new CountryNotFound("Pais no encontrado");
        JSONArray jsonResult = mapConnector.getCountryInformationByName(name);
        double latitud = Double.parseDouble(jsonResult.get(0).toString());
        double longitud = Double.parseDouble(jsonResult.get(1).toString());
        double[] datos = new double[]{latitud, longitud};
        return datos;
    }

    /**
     * Mapea la información de un json array a conjunto de paises
     * @param countriesJSON Información obtenida de la petición a la API como JSONArray
     * @return Conjunto de paises extraidos de la información.
     */
    private Set<Pais> mapJsonToSet(JSONArray countriesJSON) {
        lastModified = new Date(System.currentTimeMillis());
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

    /**
     * Mapea un objeto JSON a un POJO Ciudad.
     * @param cityJSON Objeto JSON con la información de la ciudad.
     * @return Objeto ciudad con la información traida en el JSON.
     */
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

    /**
     * Rellena el contenedor del cache.
     * @param paises Conjunto de paises obtenidos de la API.
     */
    private void fillMap(Set<Pais> paises) {
        for (Pais pais : paises) {
            this.paises.put(pais.getNombre(), pais);
        }
    }

    /**
     * Calculador de la vigencia del cache.
     * @return Valor de verdad que responde a "¿El cache es vigente?"
     */
    private boolean cacheVigente() {
        long confirmador = System.currentTimeMillis()-lastModified.getTime();
        if(confirmador>=minutosEnMilisegundos){
            return false;
        }
        return true;
    }



    @PostConstruct
    private void postConstruct() {
        lastModified = new Date(System.currentTimeMillis()-minutosEnMilisegundos-1000);
        getAllCountries();
    }
}