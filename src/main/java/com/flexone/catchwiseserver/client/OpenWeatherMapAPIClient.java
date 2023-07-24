package com.flexone.catchwiseserver.client;

import com.flexone.catchwiseserver.domain.OWMWeatherDTO;
import com.flexone.catchwiseserver.domain.OWMForecastDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class OpenWeatherMapAPIClient {

    private static final String openWeatherApiUrl = "https://api.openweathermap.org/data/2.5";
    private static final RestTemplate restTemplate = new RestTemplate();

    @Value("${OPEN_WEATHER_API_KEY}")
    String openWeatherApiKey;

    public OWMForecastDTO fetchForecast(Double lng, Double lat) {
        String url = openWeatherApiUrl + "/forecast" +
                "?lon=" + lng +
                "&lat=" + lat +
                "&units=metric" +
                "&appid=" + openWeatherApiKey;

        OWMForecastDTO response = restTemplate.getForObject(url, OWMForecastDTO.class);
        return response;
    }

    public OWMWeatherDTO fetchCurrentWeather(Double lng, Double lat) {
        String url = openWeatherApiUrl + "/weather" +
                "?lon=" + lng +
                "&lat=" + lat +
                "&units=metric" +
                "&appid=" + openWeatherApiKey;

        return restTemplate.getForObject(url, OWMWeatherDTO.class);
    }
}
