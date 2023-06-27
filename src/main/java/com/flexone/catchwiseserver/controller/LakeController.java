package com.flexone.catchwiseserver.controller;

import com.flexone.catchwiseserver.dto.FishSpeciesDTO;
import com.flexone.catchwiseserver.dto.LakeDTO;
import com.flexone.catchwiseserver.dto.LakeFishResponseDTO;
import com.flexone.catchwiseserver.dto.LakeMarkerDTO;
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

    @GetMapping("/{id}")
    public ResponseEntity<LakeDTO> getLakeById(@PathVariable("id") Long id) throws Exception {
        LakeDTO lakeDTO = lakeService.findById(id);
        return ResponseEntity.ok(lakeDTO);
    }

    @GetMapping("/markers")
    public ResponseEntity<List<LakeMarkerDTO>> getLakeMarkers(
            @RequestParam("lat") Double lat,
            @RequestParam("lng") Double lng,
            @RequestParam( defaultValue = "50000") Long range
    ) throws Exception {
        return ResponseEntity.ok(
                lakeService.findLakeMarkersInRange(lng, lat, range)
        );
    }

    @GetMapping("/{id}/fish")
    public ResponseEntity<List<FishSpeciesDTO>> getLakeFishById(@PathVariable("id") Long id) throws Exception {
        List<FishSpeciesDTO> lakeFishResponseDTOList = fishSpeciesService.findLakeFishById(id);
        log.info("fish count {}", lakeFishResponseDTOList.size());
        return ResponseEntity.ok(lakeFishResponseDTOList);
    }

}