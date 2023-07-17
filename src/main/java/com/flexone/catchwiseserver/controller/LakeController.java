package com.flexone.catchwiseserver.controller;

import com.flexone.catchwiseserver.dto.LakeDTO;
import com.flexone.catchwiseserver.dto.MapMarkerDTO;
import com.flexone.catchwiseserver.dto.LocationNameDTO;
import com.flexone.catchwiseserver.service.FishSpeciesService;
import com.flexone.catchwiseserver.service.LakeService;
import com.flexone.catchwiseserver.util.JwtAuthenticationFilter;
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
    final JwtAuthenticationFilter jwtAuthFilter;

    @GetMapping
    public ResponseEntity<List<MapMarkerDTO>> getAllLakes() {
        return null;
    }

    @GetMapping("/names")
    public ResponseEntity<List<LocationNameDTO>> getAllLakeNames(
            @RequestParam Double lng,
            @RequestParam Double lat,
            @RequestParam(required = false) Double radius
    ) {
        if (radius == null) radius = 5.0;

        List<LocationNameDTO> lakeNames = lakeService.findLakeNames(lng, lat, radius);
        return ResponseEntity.ok(lakeNames);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LakeDTO> getLakeById(@PathVariable("id") Long id) throws Exception {
        log.info("Getting lake with id " + id);
        LakeDTO lakeDTO = lakeService.findById(id);
        return ResponseEntity.ok(lakeDTO);
    }

}