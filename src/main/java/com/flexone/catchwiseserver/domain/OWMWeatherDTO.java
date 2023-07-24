package com.flexone.catchwiseserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Data
public class OWMWeatherDTO {
    private Coord coord;
    private List<Weather> weather;
    private Main main;
    private Integer visibility;
    private Wind wind;
    private Clouds clouds;
    private Double pop;
    private Rain rain;
    private Snow snow;
    private Long dt;
    private Sys sys;
    private Integer timezone;
    private String dtTxt;


    @NoArgsConstructor
    @Data
    public static class Coord {
        private Double lon;
        private Double lat;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @Data
    public static class Weather {
        private Integer id;
        private String main;
        private String description;
        private String icon;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @Data
    public static class Main {
        private Double temp;
        private Double feels_like;
        private Double temp_min;
        private Double temp_max;
        private Integer pressure;
        private Integer humidity;
        private Integer sea_level;
        private Integer grnd_level;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @Data
    public static class Wind {
        private Double speed;
        private Integer deg;
        private Double gust;
    }

    @NoArgsConstructor
    @Data
    public static class Clouds {
        private Integer all;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @Data
    public static class Rain {
        @JsonProperty("1h")
        private Double _1h;
        @JsonProperty("3h")
        private Double _3h;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @Data
    public static class Snow {
        @JsonProperty("1h")
        private Double _1h;
        @JsonProperty("3h")
        private Double _3h;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @Data
    public static class Sys {
        private Integer type;
        private Integer id;
        private String message;
        private String country;
        private Long sunrise;
        private Long sunset;
        private Character pod;
    }
}
