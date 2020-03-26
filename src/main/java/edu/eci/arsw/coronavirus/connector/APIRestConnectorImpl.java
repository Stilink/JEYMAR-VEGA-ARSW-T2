package edu.eci.arsw.coronavirus.connector;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

import edu.eci.arsw.coronavirus.exception.APIException;
import edu.eci.arsw.coronavirus.services.APIRestConnector;

/**
 * APIRestConnectorImpl
 */
@Component
public class APIRestConnectorImpl implements APIRestConnector {

    /**
     * Le solicita a la api seleccionada la información de todos los paises.
     * @return Contiene toda la información relacionada a los paises, dentro de un arreglo JSON.
     * @throws APIException En caso de que la conexión con la api falle, se lanzará esta excepción.
     */
    @Override
    public JSONArray getCountries() throws APIException{
        JSONParser parser = new JSONParser();
        JSONObject data = null;
        JSONArray countries = null;
        try {
            HttpResponse<JsonNode> response = Unirest
                    .get("https://covid-19-coronavirus-statistics.p.rapidapi.com/v1/stats")
                    .header("x-rapidapi-host", "covid-19-coronavirus-statistics.p.rapidapi.com")
                    .header("x-rapidapi-key", "763bd671e4mshdef0f3bb085ff27p10f36bjsn03e392bfc5cb").asJson();
            data =(JSONObject) parser.parse(response.getBody().toString());
            data = (JSONObject) data.get("data");
            countries = (JSONArray) data.get("covid19Stats");
                     
        } catch (UnirestException unireste) {
            throw new APIException("Fallo al conectarse con la API");
        }catch(Exception e){
            e.printStackTrace();
        }
        return countries;
    }

    /**
     * Le solicita a la api la información relacionada a un país.
     * @param name Nombre del país solicitado.
     * @return Contiene la información relacionada al país, dentro de un arreglo JSON.
     * @throws APIException En caso de que la conexión con la api falle, se lanzará esta excepción.
     */
    @Override
    public JSONArray getCountryByName(String name) throws APIException {
        JSONParser parser = new JSONParser();
        JSONObject data = null;
        JSONArray country = null;
        try {
            HttpResponse<JsonNode> response = Unirest
                    .get("https://covid-19-coronavirus-statistics.p.rapidapi.com/v1/stats?country="+name)
                    .header("x-rapidapi-host", "covid-19-coronavirus-statistics.p.rapidapi.com")
                    .header("x-rapidapi-key", "763bd671e4mshdef0f3bb085ff27p10f36bjsn03e392bfc5cb").asJson();
            data =(JSONObject) parser.parse(response.getBody().toString());
            data = (JSONObject) data.get("data");
            country = (JSONArray) data.get("covid19Stats");
                     
        } catch (UnirestException unireste) {
            throw new APIException("Fallo al conectarse con la API - Stats");
        }catch(Exception e){
            e.printStackTrace();
        }
        return country;
    }

}