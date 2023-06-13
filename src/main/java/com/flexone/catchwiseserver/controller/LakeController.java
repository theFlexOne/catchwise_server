package com.flexone.catchwiseserver.controller;

import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.dto.LakeResponse;
import com.flexone.catchwiseserver.service.LakeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wololo.geojson.GeoJSON;
import org.wololo.geojson.GeoJSONFactory;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lakes")
@RequiredArgsConstructor
@Slf4j
public class LakeController {

    final LakeService lakeService;

    @GetMapping
    public ResponseEntity<List<Lake>> getAllLakes() throws Exception {
        List<Lake> lakes = lakeService.findAllLakes();
        List<LakeResponse> lakeResponses = lakes.stream().map(lake -> {
            LakeResponse lakeResponse = new LakeResponse();
            lakeResponse.setId(lake.getId());
            lakeResponse.setName(lake.getName());
            lakeResponse.setGeometry(lake.getGeometry());
            return lakeResponse;
        }).collect(Collectors.toList());
        log.info("Found {} lakes", lakes.size());
        return ResponseEntity.ok(lakes);
    }

    @GetMapping("/within")
    public ResponseEntity<List<Lake>> getLakesInState(@RequestParam("state") String stateName) throws Exception {
        return ResponseEntity.ok(lakeService.findAllLakesInState(stateName));
    }

}
