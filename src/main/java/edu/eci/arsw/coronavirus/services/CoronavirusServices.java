package edu.eci.arsw.coronavirus.services;

import java.util.Set;

import org.springframework.stereotype.Service;

import edu.eci.arsw.coronavirus.exception.CountryNotFound;
import edu.eci.arsw.coronavirus.model.Pais;

/**
 * CoronavirusServices
 */
@Service
public interface CoronavirusServices {

    public Set<Pais> getAllCountries();

    public Pais getCountryByName(String name) throws CountryNotFound;
}