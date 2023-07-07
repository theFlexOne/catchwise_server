package com.flexone.catchwiseserver.controller;

import com.flexone.catchwiseserver.dto.LakeDTO;
import com.flexone.catchwiseserver.dto.LakeMarkerDTO;
import com.flexone.catchwiseserver.dto.LakeNameDTO;
import com.flexone.catchwiseserver.service.FishSpeciesService;
import com.flexone.catchwiseserver.service.LakeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/lakes")
@RequiredArgsConstructor
@Slf4j
public class LakeController {

    final LakeService lakeService;
    final FishSpeciesService fishSpeciesService;

    @GetMapping
    public ResponseEntity<List<LakeMarkerDTO>> getAllLakes() {
        return null;
    }

    @GetMapping("/markers")
    public ResponseEntity<List<LakeMarkerDTO>> getAllLakeMarkers(
            @RequestParam(value = "lng", required = false) Double lng,
            @RequestParam(value = "lat", required = false) Double lat,
            @RequestParam(value = "radius", required = false) Double radius
    ) {
        if (radius == null) radius = 1.0;

        List<LakeMarkerDTO> lakeDTOList = lakeService.findAllLakeMarkers(lng, lat, radius);
        return ResponseEntity.ok(lakeDTOList);
    }

    @GetMapping("/names")
    public ResponseEntity<List<LakeNameDTO>> getAllLakeNames(
            @RequestParam Double lng,
            @RequestParam Double lat,
            @RequestParam(required = false) Double radius
    ) {
        if (radius == null) radius = 5.0;

        List<LakeNameDTO> lakeNames = lakeService.findLakeNames(lng, lat, radius);
        return ResponseEntity.ok(lakeNames);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LakeDTO> getLakeById(@PathVariable("id") Long id) throws Exception {
        LakeDTO lakeDTO = lakeService.findById(id);
        return ResponseEntity.ok(lakeDTO);
    }

}