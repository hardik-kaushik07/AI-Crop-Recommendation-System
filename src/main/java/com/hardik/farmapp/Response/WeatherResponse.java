package com.hardik.farmapp.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponse  {

    private double temperature;

    private int humidity;

    private String weatherCondition;
}