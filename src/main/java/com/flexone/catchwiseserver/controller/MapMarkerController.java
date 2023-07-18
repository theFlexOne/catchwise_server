package com.flexone.catchwiseserver.controller;

import com.flexone.catchwiseserver.dto.MapMarkerDTO;
import com.flexone.catchwiseserver.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/markers")
@RequiredArgsConstructor
@Slf4j
public class MapMarkerController {

    private final LocationService locationService;

}
