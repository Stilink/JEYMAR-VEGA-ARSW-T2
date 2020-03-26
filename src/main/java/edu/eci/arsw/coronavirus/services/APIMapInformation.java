package edu.eci.arsw.coronavirus.services;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;

import edu.eci.arsw.coronavirus.exception.APIException;

/**
 * APIMapInformation
 */
@Service
public interface APIMapInformation {

    public JSONArray getCountryInformationByName(String name) throws APIException;
}