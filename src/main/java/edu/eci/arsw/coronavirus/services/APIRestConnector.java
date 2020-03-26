package edu.eci.arsw.coronavirus.services;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;

import edu.eci.arsw.coronavirus.exception.APIException;

/**
 * APIRestConnector
 */
@Service
public interface APIRestConnector {

    /**
     * Le solicita a la api seleccionada la información de todos los paises.
     * @return Contiene toda la información relacionada a los paises, dentro de un arreglo JSON.
     * @throws APIException En caso de que la conexión con la api falle, se lanzará esta excepción.
     */
    public JSONArray getCountries() throws APIException;

    /**
     * Le solicita a la api la información relacionada a un país.
     * @param name Nombre del país solicitado.
     * @return Contiene la información relacionada al país, dentro de un arreglo JSON.
     * @throws APIException En caso de que la conexión con la api falle, se lanzará esta excepción.
     */
    public JSONArray getCountryByName(String name) throws APIException;
}