package com.flexone.catchwiseserver.controller;

import com.flexone.catchwiseserver.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/current")
    public ResponseEntity<?> getWeather(
            @RequestParam Double lng,
            @RequestParam Double lat
    ) {
        return ResponseEntity.ok(weatherService.fetchCurrentWeather(lng, lat));
    }

    @GetMapping("/forecast")
    public ResponseEntity<?> getForecast(
            @RequestParam Double lng,
            @RequestParam Double lat
    ) {
        return ResponseEntity.ok(weatherService.fetchForecast(lng, lat));
    }


}
