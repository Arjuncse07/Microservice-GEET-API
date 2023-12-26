package com.arjun.WeatherApplication.controller;

import com.arjun.WeatherApplication.model.WeatherData;
import com.arjun.WeatherApplication.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {




    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    @GetMapping("/{city}")
    public WeatherData getWeatherInformation(@PathVariable String city){
        return weatherService.getWeatherData(city);
    }



}
