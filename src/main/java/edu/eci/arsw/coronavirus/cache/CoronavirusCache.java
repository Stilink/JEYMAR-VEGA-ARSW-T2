package edu.eci.arsw.coronavirus.cache;

import java.util.Set;

import org.springframework.stereotype.Service;

import edu.eci.arsw.coronavirus.exception.CountryNotFound;
import edu.eci.arsw.coronavirus.model.Pais;

/**
 * CoronavirusCache
 */
@Service
public interface CoronavirusCache {

    /**
     * Solicita a la api relacionada la información de todos los paises
     * @return Conjunto con toda la información
     */
    public Set<Pais> getAllCountries();

    /**
     * Solicita a la api relacionada la información de un país en especifico
     * @param country Nombre del país solicitado
     * @return Pais solicitado
     */
    public Pais getCountryByName(String country) throws CountryNotFound;
    
}