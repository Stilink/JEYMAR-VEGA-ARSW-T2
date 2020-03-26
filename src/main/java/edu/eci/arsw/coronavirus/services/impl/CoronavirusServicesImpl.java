package edu.eci.arsw.coronavirus.services.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.eci.arsw.coronavirus.cache.CoronavirusCache;
import edu.eci.arsw.coronavirus.exception.APIException;
import edu.eci.arsw.coronavirus.exception.CountryNotFound;
import edu.eci.arsw.coronavirus.model.Pais;
import edu.eci.arsw.coronavirus.services.CoronavirusServices;

/**
 * CoronavirusServicesImpl Utiliza un "cache" para solicitar la información
 * interesante.
 */
@Component
public class CoronavirusServicesImpl implements CoronavirusServices {

    @Autowired
    private CoronavirusCache coronavirusCache;

    /**
     * Retorna un conjunto que contiene la información de todos los paises.
     * 
     * @return Conjuntos con la información.
     */
    @Override
    public Set<Pais> getAllCountries() {
        Set<Pais> countries = coronavirusCache.getAllCountries();
        return countries;
    }

    /**
     * Solicitud de un País en especifico.
     * 
     * @param name Nombre del País solicitado.
     * @return Pais solicitado.
     * @throws CountryNotFound En caso de no encontrar el país se lanza esta
     *                         excepción.
     */
    @Override
    public Pais getCountryByName(String name) throws CountryNotFound {
        Pais country = coronavirusCache.getCountryByName(name);
        return country;
    }


    /**
     * Solicitud de la ubicación geografica de un país
     * @param pais Nombre del país solicitado
     * @return Un arreglo de dos posiciones con la latitud y la longitud
     * @throws CountryNotFound En caso de no encontrar el país
     * @throws APIException En caso de que la api falle
     */
    @Override
    public double[] getMapInfoByCountry(String pais) throws CountryNotFound, APIException {
        double[] datos = coronavirusCache.getMapInfoByCountry(pais);
        return datos;
    }

    
    
    
}