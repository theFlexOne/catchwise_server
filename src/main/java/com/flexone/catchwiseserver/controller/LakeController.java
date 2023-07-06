package com.flexone.catchwiseserver.controller;

import com.flexone.catchwiseserver.dto.LakeDTO;
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
    public ResponseEntity<List<LakeMarkerDTO>> getNearestLakeMarkers(
            @RequestParam double lat,
            @RequestParam double lng
    ) throws Exception {
        List<LakeMarkerDTO> lakeMarkerDTOList = lakeService.findAllLakeMarkersInRange((int) lat, (int) lng);
        return ResponseEntity.ok(lakeMarkerDTOList);
    }


}