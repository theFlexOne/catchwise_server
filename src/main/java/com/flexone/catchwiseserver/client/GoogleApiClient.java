package com.flexone.catchwiseserver.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.flexone.catchwiseserver.dto.GoogleApiJSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
@Slf4j
public class GoogleApiClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl = "https://maps.googleapis.com/maps/api";

    @Value("${google.api.key}")
    String googleApiKey;


    public GoogleApiJSON getPlaceSearch(String query, String fields, String inputType, String output) {
        String placesSearchUrl = apiUrl + "/place/findplacefromtext";
        inputType = inputType == null ? "textquery" : inputType;
        output = output == null ? "json" : output;
        fields = fields.length() == 0 ? "name,place_id" : fields;
        String url = placesSearchUrl +
                "/" + output +
                "?input=" + query +
                "&inputtype=" + inputType +
                "&fields=" + fields +
                "&key=" + googleApiKey;

        ResponseEntity<GoogleApiJSON> response = restTemplate.getForEntity(url, GoogleApiJSON.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info(response.getBody().toString());
            return response.getBody();
        }
        return null;
    }
    public Object getPlaceSearch(String query, String fields, String inputType) {
        return getPlaceSearch(query, fields, inputType, null);
    }

    public GoogleApiJSON getPlaceDetails(String placeId, String fields, String output) {
        String placesDetailsUrl = apiUrl + "/place/details";
        output = output == null ? "json" : output;
        fields = fields == null || fields.length() == 0 ? "name, place_id" : fields;
        String url = placesDetailsUrl +
                "/" + output +
                "?place_id=" + placeId +
                "&fields=" + fields +
                "&key=" + googleApiKey;

        ResponseEntity<GoogleApiJSON> response = restTemplate.getForEntity(url, GoogleApiJSON.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info(response.getBody().toString());
            return response.getBody();
        }
        return null;
    }
    public Object getPlaceDetails(String placeId, String fields) {
        return getPlaceDetails(placeId, fields, null);
    }



}
