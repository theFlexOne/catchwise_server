package com.flexone.catchwiseserver.controller;

import com.flexone.catchwiseserver.dto.LakeDTO;
import com.flexone.catchwiseserver.dto.LocationDTO;
import com.flexone.catchwiseserver.service.FishSpeciesService;
import com.flexone.catchwiseserver.service.LakeService;
import com.flexone.catchwiseserver.security.JWTAuthenticationFilter;
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
    final JWTAuthenticationFilter jwtAuthFilter;

    @GetMapping
    public ResponseEntity<List<LocationDTO>> getAllLakes() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LakeDTO> getLakeById(@PathVariable("id") Long id) throws Exception {
        log.info("Getting lake with id " + id);
        LakeDTO lakeDTO = lakeService.findById(id);
        return ResponseEntity.ok(lakeDTO);
    }

}