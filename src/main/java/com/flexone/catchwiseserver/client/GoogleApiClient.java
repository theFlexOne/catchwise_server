package com.flexone.catchwiseserver.client;

import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

@RequiredArgsConstructor
@Component
@Slf4j
public class GoogleApiClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl = "https://maps.googleapis.com/maps/api";

    @Value("${google.api.key}")
    String googleApiKey;


    public JSONPObject getPlaceSearch(String query, String[] fields, String inputType, String output) {
        String placesSearchUrl = apiUrl + "/place/findplacefromtext";
        inputType = inputType == null ? "textquery" : inputType;
        output = output == null ? "json" : output;
        fields = fields.length == 0 ? new String[]{"name", "place_id"} : fields;
        String url = placesSearchUrl +
                "/" + output +
                "?input=" + query +
                "&inputtype=" + inputType +
                "&fields=" + fields +
                "&key=" + googleApiKey;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        log.info(response.getStatusCode().toString());
        return new JSONPObject("callback", response.getBody());
    }
    public Object getPlaceSearch(String query, String[] fields, String inputType) {
        return getPlaceSearch(query, fields, inputType, null);
    }

    public Object getPlaceDetails(String placeId, String fields, String output) {
        String placesDetailsUrl = apiUrl + "/place/details";
        output = output == null ? "json" : output;
        fields = fields == null || fields.length() == 0 ? "name, place_id" : fields;
        String url = placesDetailsUrl +
                "/" + output +
                "?place_id=" + placeId +
                "&fields=" + fields +
                "&key=" + googleApiKey;

        return restTemplate.getForObject(url, Object.class);
    }
    public Object getPlaceDetails(String placeId, String fields) {
        return getPlaceDetails(placeId, fields, null);
    }



}
