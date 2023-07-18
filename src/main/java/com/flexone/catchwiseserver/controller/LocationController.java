package com.flexone.catchwiseserver.controller;

import com.flexone.catchwiseserver.domain.LocationName;
import com.flexone.catchwiseserver.dto.LocationNameDTO;
import com.flexone.catchwiseserver.dto.LocationDTO;
import com.flexone.catchwiseserver.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/markers")
    public ResponseEntity<List<LocationDTO>> getAllLakeMarkers(
            @RequestParam(value = "lng", required = false) Double lng,
            @RequestParam(value = "lat", required = false) Double lat,
            @RequestParam(value = "radius", required = false) Double radius
    ) {
        List<LocationDTO> locationDTOList = locationService.findAllLocations(lng, lat, radius);
        log.info("Returning " + locationDTOList.size() + " lakes");
        return ResponseEntity.ok(locationDTOList);
    }



    @GetMapping("/names")
    public ResponseEntity<List<LocationNameDTO>> getAllLocationNames(
            @RequestParam(value = "lng", required = false) Double lng,
            @RequestParam(value = "lat", required = false) Double lat,
            @RequestParam(value = "radius", required = false) Double radius
    ) {
        radius = radius == null ? 5.0 : radius;
        List<LocationNameDTO> locationNames = locationService.getAllLocationNames(lng, lat, radius);
        return ResponseEntity.ok(locationNames);
    }

    @GetMapping("/names/search")
    public ResponseEntity<List<LocationName>> searchLocationNamesInRadius(
            @RequestParam(value = "q", required = false) String query,
            @RequestParam(value = "lng", required = false) Double lng,
            @RequestParam(value = "lat", required = false) Double lat,
            @RequestParam(value = "radius", required = false) Double radius
    ) {
        List<LocationName> locationNames = locationService.searchLocationNames(query, lng, lat, radius);
        return ResponseEntity.ok(locationNames);
    }



}
