package com.flexone.catchwiseserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.dto.LakeJSON;
import com.flexone.catchwiseserver.dto.LakeResponse;
import com.flexone.catchwiseserver.service.LakeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wololo.geojson.GeoJSON;
import org.wololo.geojson.GeoJSONFactory;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lakes")
@RequiredArgsConstructor
@Slf4j
public class LakeController {

    final LakeService lakeService;

    @GetMapping("/{id}")
    public ResponseEntity<LakeResponse> getLakeById(@PathVariable("id") Long id) throws Exception {
        Lake lake = lakeService.findLakeById(id);
        log.info("Returning lake {}", lake.getName());
        log.info("Returning lake {}", lake.getGeometry());
        LakeResponse lakeResponse = mapLakeToLakeResponse(lake);
        return ResponseEntity.ok(lakeResponse);
    }

    @GetMapping
    public ResponseEntity<List<LakeResponse>> getAllLakes() {
        List<Lake> lakes = lakeService.findAllLakes();
        log.info("Returning {} lakes", lakes.size());
        List<LakeResponse> lakeResponses = lakes.stream()
                .map(lake -> {
                    try {
                        return mapLakeToLakeResponse(lake);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(lakeResponses);
    }

    @GetMapping("/in-range")
    public ResponseEntity<List<LakeResponse>> getLakesInRange(
            @RequestParam("lat") Double lat,
            @RequestParam("lng") Double lng,
            @RequestParam("range") Integer range
    ) {
        List<Lake> lakes = lakeService.findLakesInRange(lat, lng, range);
        log.info("Returning {} lakes", lakes.size());
        List<LakeResponse> lakeResponses = lakes.stream()
                .map(lake -> {
                    try {
                        return mapLakeToLakeResponse(lake);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(lakeResponses);
    }

    private LakeResponse mapLakeToLakeResponse(Lake lake) throws JsonProcessingException {
        Double lat = lake.getGeometry().getX();
        Double lng = lake.getGeometry().getY();
        return new LakeResponse()
                .setId(lake.getId())
                .setName(lake.getName())
                .setCoordinates(new Double[]{lat, lng});
    }


}
