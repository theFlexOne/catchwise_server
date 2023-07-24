package com.flexone.catchwiseserver.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class WeatherResponseDTO {
    private String main;
    private String description;
    private Double temp;
    private Integer pressure;
    private Integer humidity;
    private Double windSpeed;
    private Integer windDirection;
    private Double clouds;
    private Date sunrise;
    private Date sunset;
    private Date dt;
    private Long timezone;
    private Coords coords;

    @Data
    @Accessors(chain = true)
    public static class Coords {
        private Double lng;
        private Double lat;
    }
}
