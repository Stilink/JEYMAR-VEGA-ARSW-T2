package edu.eci.arsw.coronavirus.cache;

import java.util.Set;

import org.springframework.stereotype.Service;

import edu.eci.arsw.coronavirus.model.Pais;

/**
 * CoronavirusCache
 */
@Service
public interface CoronavirusCache {

    public Set<Pais> getAllCountries();

    public Pais getCountryByName(String country);
    
}