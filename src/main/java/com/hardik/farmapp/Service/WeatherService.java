package com.hardik.farmapp.Service;

import com.hardik.farmapp.Response.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherResponse getWeather(String location){

        String url =
                "https://api.openweathermap.org/data/2.5/weather?q="
                        + location
                        + "&appid="
                        + apiKey
                        + "&units=metric";

        Map<String,Object> response =
                restTemplate.getForObject(url, Map.class);

        Map<String,Object> main =
                (Map<String,Object>) response.get("main");

        double temperature =
                ((Number) main.get("temp")).doubleValue();

        int humidity =
                ((Number) main.get("humidity")).intValue();

        Map<String,Object> weather =
                ((java.util.List<Map<String,Object>>) response.get("weather"))
                        .get(0);

        String weatherCondition =
                weather.get("main").toString();

        return new WeatherResponse(
                temperature,
                humidity,
                weatherCondition
        );

    }
}
