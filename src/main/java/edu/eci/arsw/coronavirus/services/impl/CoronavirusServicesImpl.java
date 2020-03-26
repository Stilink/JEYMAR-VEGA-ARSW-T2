package edu.eci.arsw.coronavirus.services.impl;

import java.util.Set;

import org.springframework.stereotype.Component;

import edu.eci.arsw.coronavirus.exception.CountryNotFound;
import edu.eci.arsw.coronavirus.model.Pais;
import edu.eci.arsw.coronavirus.services.CoronavirusServices;

/**
 * CoronavirusServicesImpl
 */
@Component
public class CoronavirusServicesImpl implements CoronavirusServices {

    
    @Override
    public Set<Pais> getAllCountries() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pais getCountryByName(String name) throws CountryNotFound {
        // TODO Auto-generated method stub
        return null;
    }

    
}