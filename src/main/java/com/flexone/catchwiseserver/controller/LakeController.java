package com.flexone.catchwiseserver.controller;

import com.flexone.catchwiseserver.domain.LakeMarker;
import com.flexone.catchwiseserver.dto.LakeDTO;
import com.flexone.catchwiseserver.dto.LakeMarkerDTO;
import com.flexone.catchwiseserver.dto.LakeNameDTO;
import com.flexone.catchwiseserver.mapper.LakeMarkerMapper;
import com.flexone.catchwiseserver.service.FishSpeciesService;
import com.flexone.catchwiseserver.service.LakeMarkerService;
import com.flexone.catchwiseserver.service.LakeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/lakes")
@RequiredArgsConstructor
@Slf4j
public class LakeController {

    final LakeService lakeService;
    final LakeMarkerService lakeMarkerService;
    final FishSpeciesService fishSpeciesService;
    final LakeMarkerMapper lakeMarkerMapper;


    @GetMapping("/{id}")
    public ResponseEntity<LakeDTO> getLakeById(@PathVariable("id") Long id) throws Exception {
        LakeDTO lakeDTO = lakeService.findById(id);
        return ResponseEntity.ok(lakeDTO);
    }


    @GetMapping("/{id}/marker")
    public ResponseEntity<LakeMarkerDTO> getNearestLakeMarkerById(@PathVariable("id") Number id) throws Exception {
        LakeMarker lakeMarker = lakeMarkerService.findByLakeId(id);
        LakeMarkerDTO lakeMarkerDTO = lakeMarkerMapper.toDto(lakeMarker);
        return ResponseEntity.ok(lakeMarkerDTO);
    }


    @GetMapping("/marker")
    public ResponseEntity<LakeMarkerDTO> getNearestLakeMarker(
            @RequestParam Double lat,
            @RequestParam Double lng
    ) throws Exception {
        LakeMarkerDTO lakeMarkerDTO = lakeMarkerService.findNearestLakeMarker(lat, lng);
        return ResponseEntity.ok(lakeMarkerDTO);
    }


    @GetMapping("/markers")
    public ResponseEntity<List<LakeMarkerDTO>> getNearestLakeMarkers(
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam(defaultValue = "25") Integer miles,
            @RequestParam(required = false, name = "fish") String fishSpecies
    ) throws Exception {
        List<LakeMarkerDTO> lakeMarkerDTOList = new ArrayList<>();
        if (fishSpecies != null) {
            lakeMarkerDTOList = lakeMarkerService.findAllLakeMarkersInRangeByFishSpecies(lng, lat, miles, fishSpecies);
            return ResponseEntity.ok(lakeMarkerDTOList);
        } else {
            lakeMarkerDTOList = lakeMarkerService.findNearestLakeMarkers(lng, lat);
            return ResponseEntity.ok(lakeMarkerDTOList);
        }
    }

    @GetMapping("/names")
    public ResponseEntity<List<LakeNameDTO>> getNearestLakeNames(
            @RequestParam Double lat,
            @RequestParam Double lng
    ) {
        List<LakeNameDTO> lakeNames = lakeService.findAllLakeNames(lng, lat);
        return ResponseEntity.ok(lakeNames);
    }

}