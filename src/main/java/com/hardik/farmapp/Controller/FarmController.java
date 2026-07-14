package com.hardik.farmapp.Controller;

import com.hardik.farmapp.Entity.FarmAnalysis;
import com.hardik.farmapp.Request.FarmRequest;
import com.hardik.farmapp.Response.WeatherResponse;
import com.hardik.farmapp.Service.FarmService;
import com.hardik.farmapp.Service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farm")
public class FarmController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private FarmService farmService;

    @PostMapping("/weather")
    public WeatherResponse getWeather(
            @RequestBody FarmRequest analysis){

        return weatherService.getWeather(
                analysis.getLocation()
        );

    }

    @GetMapping("/history")
    public List<FarmAnalysis> getHistory(Authentication authentication,
                                         @RequestParam(defaultValue = "0") int pageNumber,
                                         @RequestParam(defaultValue = "5") int pageSize){
        return farmService.getHistory(authentication, pageNumber,pageSize);
    }

    @GetMapping("/history/{id}")
    public FarmAnalysis getHistory(@PathVariable Long id, Authentication authentication){
        return farmService.getHistoryById(id, authentication);
    }

    @DeleteMapping("/delete")
    public String deleteAllHistory(Authentication authentication){
        String deleteInfo = farmService.deleteAllHistory(authentication);
        return deleteInfo;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteHistoryById(@PathVariable Long id,  Authentication authentication){
        String deleteInfo = farmService.deleteHistoryById(id, authentication);
        return deleteInfo;
    }
}
