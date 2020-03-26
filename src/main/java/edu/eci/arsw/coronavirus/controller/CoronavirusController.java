package edu.eci.arsw.coronavirus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.arsw.coronavirus.services.CoronavirusServices;

/**
 * CoronavirusController
 */
@RestController
public class CoronavirusController {

    @Autowired
    private CoronavirusServices coronavirusServices;

    
}