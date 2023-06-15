package com.flexone.catchwiseserver.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.flexone.catchwiseserver.client.GoogleApiClient;
import com.flexone.catchwiseserver.dto.GoogleApiJSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/google")
public class GoogleApiController {

    private static final GoogleApiClient googleApiClient = new GoogleApiClient();
    @GetMapping("/place/search")
    public ResponseEntity<Object> getPlaceSearch(
            @RequestParam String query,
            @RequestParam(required = false, name = "inputtype") String inputType,
            @RequestParam(required = false) String output,
            @RequestParam(required = false) String... fields
    ) {
        log.info("fields: " + Arrays.toString(fields));
        Object response = googleApiClient.useGooglePlaceSearchApi(query, fields, inputType, output);
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }


    @GetMapping("/place/details")
    public ResponseEntity<Object> getPlaceDetails(
            @RequestParam(name = "place_id") String placeId,
            @RequestParam(required = false) String output,
            @RequestParam(required = false) String... fields
    ) {
        log.info("fields: " + Arrays.toString(fields));
        Object response = googleApiClient.useGooglePlaceDetailsApi(placeId, output, fields);
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }


    @GetMapping("/place/photo")
    public ResponseEntity<Object> getPlacePhoto(
            @RequestParam(name = "photo_reference") String photoReference,
            @RequestParam(required = false) String maxwidth,
            @RequestParam(required = false) String maxheight
    ) {
        Object response = googleApiClient.useGooglePlacePhotoApi(photoReference, maxwidth, maxheight);
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }


    @GetMapping("/elevation")
    public ResponseEntity<Object> getElevation(
            @RequestParam Double lat,
            @RequestParam Double lng
    ) {
        String locations = lat + "," + lng;
        Object response = googleApiClient.useGoogleElevationApi(locations);
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }


    @GetMapping("/reverse-geocode")
    public ResponseEntity<Object> getReverseGeocode(
            @RequestParam Double lat,
            @RequestParam Double lng
    ) {
        String locations = lat + "," + lng;
        Object response = googleApiClient.useGoogleReverseGeocodeApi(locations);
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }

}

