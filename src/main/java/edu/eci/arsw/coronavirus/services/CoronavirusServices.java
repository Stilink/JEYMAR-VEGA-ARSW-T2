package edu.eci.arsw.coronavirus.services;

import java.util.Set;

import org.springframework.stereotype.Service;

import edu.eci.arsw.coronavirus.exception.APIException;
import edu.eci.arsw.coronavirus.exception.CountryNotFound;
import edu.eci.arsw.coronavirus.model.Pais;

/**
 * CoronavirusServices
 */
@Service
public interface CoronavirusServices {
    
    /**
     * Retorna un conjunto que contiene la información de todos los paises.
     * @return Conjuntos con la información.
     */
    public Set<Pais> getAllCountries();

    /**
     * Solicitud de un País en especifico.
     * @param name Nombre del País solicitado.
     * @return  Pais solicitado.
     * @throws CountryNotFound En caso de no encontrar el país se lanza esta excepción.
     */
    public Pais getCountryByName(String name) throws CountryNotFound;
    
    /**
     * Solicitud de la ubicación geografica de un país
     * @param pais Nombre del país solicitado
     * @return Un arreglo de dos posiciones con la latitud y la longitud
     * @throws CountryNotFound En caso de no encontrar el país
     * @throws APIException En caso de que la api falle.
     */
	public double[] getMapInfoByCountry(String pais) throws CountryNotFound, APIException;
}