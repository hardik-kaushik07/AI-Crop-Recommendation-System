package com.hardik.farmapp.Service;

import com.hardik.farmapp.Response.WeatherResponse;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherTools {

    @Autowired
    private WeatherService weatherService;

    @Tool(description = "Get current weather information of a location")
    public WeatherResponse getWeather(String location){
        return weatherService.getWeather(location);
    }


}
