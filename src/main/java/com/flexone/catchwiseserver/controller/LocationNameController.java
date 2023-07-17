package com.flexone.catchwiseserver.controller;

import com.flexone.catchwiseserver.domain.LocationName;
import com.flexone.catchwiseserver.dto.LocationNameDTO;
import com.flexone.catchwiseserver.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location-names")
@RequiredArgsConstructor
public class LocationNameController {

    private final LocationService locationService;


    @GetMapping
    public ResponseEntity<List<LocationNameDTO>> getAllLocationNames(
            @RequestParam(value = "lng", required = false) Double lng,
            @RequestParam(value = "lat", required = false) Double lat,
            @RequestParam(value = "radius", required = false) Double radius
    ) {
        radius = radius == null ? 5.0 : radius;
        List<LocationName> locationNames = locationService.getAllLocationNames(lng, lat, radius);
        return ResponseEntity.ok(LocationNameDTO.from(locationNames));
    }

    @GetMapping("/search")
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
