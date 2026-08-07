package com.hardik.farmapp.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;

import com.hardik.farmapp.Service.WeatherTools;

@Configuration
public class ToolConfiguration {

    @Bean
    ToolCallbackProvider toolCallbackProvider(
            WeatherTools weatherTools) {

        return MethodToolCallbackProvider.builder()
                .toolObjects(weatherTools)
                .build();
    }
}
