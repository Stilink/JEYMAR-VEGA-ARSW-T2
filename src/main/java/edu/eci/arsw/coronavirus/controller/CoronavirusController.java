package edu.eci.arsw.coronavirus.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.arsw.coronavirus.exception.APIException;
import edu.eci.arsw.coronavirus.exception.CountryNotFound;
import edu.eci.arsw.coronavirus.services.CoronavirusServices;

/**
 * CoronavirusController
 */
@RestController
public class CoronavirusController {

    @Autowired
    private CoronavirusServices coronavirusServices;

    @RequestMapping(value = "/coronavirus", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCountries() {
        try {
            return new ResponseEntity<>(coronavirusServices.getAllCountries(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error del servidor - ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/coronavirus/{pais}", method = RequestMethod.GET)
    public ResponseEntity<?> getCountry(@PathVariable String pais){
        try {
            return new ResponseEntity<>(coronavirusServices.getCountryByName(pais), HttpStatus.ACCEPTED);
        } catch (CountryNotFound cne) {
            cne.printStackTrace();
            return new ResponseEntity<>("Fallo en la obtención del país - NOT FOUND", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/coronavirus/{pais}/provinces", method = RequestMethod.GET)
    public ResponseEntity<?> getProvincesByCountry(@PathVariable String pais){
        try {
            return new ResponseEntity<>(coronavirusServices.getCountryByName(pais).getProvincias().values(),
                    HttpStatus.ACCEPTED);
        } catch (CountryNotFound cne) {
            cne.printStackTrace();
            return new ResponseEntity<>("Fallo en la obtención del país - NOT FOUND", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/coronavirus/{pais}/information", method = RequestMethod.GET)
    public ResponseEntity<?> getMapInfoByCountry(@PathVariable String pais){
        try{
            double[] info = coronavirusServices.getMapInfoByCountry(pais);
            return new ResponseEntity<>(Arrays.toString(info),HttpStatus.ACCEPTED);        
        }catch(CountryNotFound cne){
            cne.printStackTrace();
            return new ResponseEntity<>("Fallo en la obtención del país - NOT FOUND", HttpStatus.NOT_FOUND);
        }catch(APIException ape){
            ape.printStackTrace();
            return new ResponseEntity<>("Error interno - ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    
    
}