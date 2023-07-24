package com.flexone.catchwiseserver.service;

import com.flexone.catchwiseserver.client.OpenWeatherMapAPIClient;
import com.flexone.catchwiseserver.domain.OWMForecastDTO;
import com.flexone.catchwiseserver.domain.OWMWeatherDTO;
import com.flexone.catchwiseserver.dto.WeatherResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    private final OpenWeatherMapAPIClient openWeatherMapAPIClient;

    public WeatherResponseDTO fetchCurrentWeather(Double lng, Double lat) {
        OWMWeatherDTO owmWeatherDTO = openWeatherMapAPIClient.fetchCurrentWeather(lng, lat);

        Double clouds = owmWeatherDTO.getClouds().getAll() / 100.0;
        WeatherResponseDTO.Coords coords = new WeatherResponseDTO.Coords()
                .setLat(owmWeatherDTO.getCoord().getLat())
                .setLng(owmWeatherDTO.getCoord().getLon());

        log.info("sunset: " + owmWeatherDTO.getSys().getSunset() + " sunrise: " + owmWeatherDTO.getSys().getSunrise());
        log.info("datetime: " + owmWeatherDTO.getDt() + " timezone: " + owmWeatherDTO.getTimezone());


        return new WeatherResponseDTO()
                .setMain(owmWeatherDTO.getWeather().get(0).getMain())
                .setDescription(owmWeatherDTO.getWeather().get(0).getDescription())
                .setTemp(owmWeatherDTO.getMain().getTemp())
                .setPressure(owmWeatherDTO.getMain().getPressure())
                .setHumidity(owmWeatherDTO.getMain().getHumidity())
                .setWindSpeed(owmWeatherDTO.getWind().getSpeed())
                .setWindDirection(owmWeatherDTO.getWind().getDeg())
                .setClouds(clouds)
                .setSunrise(new Date(owmWeatherDTO.getSys().getSunrise() * 1000))
                .setSunset(new Date(owmWeatherDTO.getSys().getSunset() * 1000))
                .setDt(new Date(owmWeatherDTO.getDt() * 1000))
                .setTimezone(Long.valueOf(owmWeatherDTO.getTimezone()) * 1000)
                .setCoords(coords);
    }
    public OWMForecastDTO fetchForecast(Double lng, Double lat) {
        return openWeatherMapAPIClient.fetchForecast(lng, lat);
    }



}
