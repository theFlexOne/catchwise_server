package com.flexone.catchwiseserver.controller;

import com.flexone.catchwiseserver.dto.MapMarkerDTO;
import com.flexone.catchwiseserver.service.MapMarkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
public class MapController {

    private final MapMarkerService mapMarkerService;

    @GetMapping("/markers")
    public List<MapMarkerDTO> getMarkers(
            @RequestParam(value = "lng", required = true) Double lng,
            @RequestParam(value = "lat", required = true) Double lat,
            @RequestParam(value = "radius") Double radius,
            @RequestParam(value = "fields") String fields
    ) {
        if (fields == null) fields = "all";
        List<String> fieldsList = Arrays.asList(fields.split(","));

        return mapMarkerService.getMarkers(lng, lat, radius, fieldsList);
    }

}
