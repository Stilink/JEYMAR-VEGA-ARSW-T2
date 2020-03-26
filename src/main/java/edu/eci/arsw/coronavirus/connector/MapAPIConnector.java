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
import edu.eci.arsw.coronavirus.services.APIMapInformation;

/**
 * MapAPIConnector
 */
@Component
public class MapAPIConnector implements APIMapInformation{

    @Override
    public JSONArray getCountryInformationByName(String name) throws APIException {
        JSONParser parser = new JSONParser();
        JSONArray data = null;
        JSONObject countryInfo = null;
        JSONArray info = null;
        try {
            HttpResponse<JsonNode> response = Unirest
                    .get("https://restcountries-v1.p.rapidapi.com/name/"+name)
                    .header("x-rapidapi-host", "restcountries-v1.p.rapidapi.com")
                    .header("x-rapidapi-key", "763bd671e4mshdef0f3bb085ff27p10f36bjsn03e392bfc5cb").asJson();
            data =(JSONArray) parser.parse(response.getBody().toString());
            countryInfo = (JSONObject) data.get(0);
            info = (JSONArray) countryInfo.get("latlng");
        } catch (UnirestException unireste) {
            throw new APIException("Fallo al conectarse con la API - Ubicaciones");
        }catch(Exception e){
            e.printStackTrace();
        }
        return info;
    }
}