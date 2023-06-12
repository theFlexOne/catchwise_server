package com.flexone.catchwiseserver.controller;

import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.service.LakeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lakes")
@RequiredArgsConstructor
@Slf4j
public class LakeController {

    final LakeService lakeService;


    @GetMapping
    public ResponseEntity<List<Lake>> getLakes(@RequestParam("state") String stateName) throws Exception {
        return ResponseEntity.ok(lakeService.findAllLakesInState(stateName));
    }

}
