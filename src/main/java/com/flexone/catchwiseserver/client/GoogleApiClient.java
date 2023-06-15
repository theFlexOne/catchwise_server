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

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String placesSearchUrl = "https://maps.googleapis.com/maps/api/place/findplacefromtext";
    private static final String placesDetailsUrl = "https://maps.googleapis.com/maps/api/place/details";
    private static final String placesPhotoUrl = "https://maps.googleapis.com/maps/api/place/photo";
    private static final String elevationUrl = "https://maps.googleapis.com/maps/api/elevation";
    private static final String reverseGeocodeUrl = "https://maps.googleapis.com/maps/api/geocode";

    @Value("${google.api.key}")
    String googleApiKey;

    public Object useGooglePlaceSearchApi(String query, String[] fields, String inputType, String output) {
        inputType = inputType == null ? "textquery" : inputType;
        output = output == null ? "json" : output;
        fields = fields.length == 0 ? new String[]{"name", "place_id"} : fields;
        String url = placesSearchUrl +
                "/" + output +
                "?input=" + query +
                "&inputtype=" + inputType +
                "&fields=" + String.join(",", fields) +
                "&key=" + googleApiKey;

        return restTemplate.getForObject(url, Object.class);
    }

    public Object useGooglePlaceDetailsApi(String placeId, String output, String[] fields) {
        output = output == null ? "json" : output;
        fields = fields == null || fields.length == 0 ? new String[]{"name", "place_id"} : fields;
        String url = placesDetailsUrl +
                "/" + output +
                "?place_id=" + placeId +
                "&fields=" + String.join(",", fields) +
                "&key=" + googleApiKey;

        return restTemplate.getForObject(url, Object.class);
    }

    public byte[] useGooglePlacePhotoApi(String photoReference, String maxwidth, String maxheight) {
        maxwidth = maxwidth == null ? "400" : maxwidth;

        String url = placesPhotoUrl +
                "?photo_reference=" + photoReference +
                "&maxwidth=" + maxwidth +
                "&key=" + googleApiKey;

        // fetch the photo from Google and respond with the byte array
        return restTemplate.getForObject(url, byte[].class);
    }

    public Object useGoogleElevationApi(String locations, String output) {
        output = output == null ? "json" : output;
        String url = elevationUrl +
                "/" + output +
                "?locations=" + locations +
                "&key=" + googleApiKey;

        return restTemplate.getForObject(url, Object.class);
    }
    public Object useGoogleElevationApi(String locations) {
        return useGoogleElevationApi(locations, "json");
    }

    public Object useGoogleReverseGeocodeApi(String locations, String output) {
        String url = reverseGeocodeUrl +
                "/" + output +
                "?latlng=" + locations +
                "&location_type=GEOMETRIC_CENTER" +
                "&key=" + googleApiKey;

        return restTemplate.getForObject(url, Object.class);
    }
    public Object useGoogleReverseGeocodeApi(String locations) {
        return useGoogleReverseGeocodeApi(locations, "json");
    }


}
