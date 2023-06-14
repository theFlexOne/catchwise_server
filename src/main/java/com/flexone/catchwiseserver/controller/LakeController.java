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
        LakeResponse lakeResponse = new LakeResponse()
                .setId(lake.getId())
                .setName(lake.getName())
                .setGeometry(lake.getGeometry());
        return new ResponseEntity<>(lakeResponse, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<LakeResponse>> getAllLakes() throws Exception {
        List<Lake> lakes = lakeService.findAllLakes();
        log.info("Returning {} lakes", lakes.size());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/within")
    public ResponseEntity<List<Lake>> getLakesInState(@RequestParam("state") String stateName) throws Exception {
        return ResponseEntity.ok(lakeService.findAllLakesInState(stateName));
    }

    private LakeResponse mapLakeToLakeResponse(Lake lake) throws JsonProcessingException {
        return new LakeResponse()
                .setId(lake.getId())
                .setName(lake.getName())
                .setGeometry(lake.getGeometry());
    }


}
