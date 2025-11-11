package com.cfs.Weather_App.Controller;

import com.cfs.Weather_App.Service.WeatherService;
import com.cfs.Weather_App.dto.myweatherForcast;
import com.cfs.Weather_App.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/weather")
@CrossOrigin(origins = "*")   // 👉 yeh line add karo


public class Controller {
    @Autowired
    private WeatherService weatherService;



    @GetMapping("/{city}")
    public String getWeatherdata(@PathVariable  String city) {
        return weatherService.test();
    }

    @GetMapping("/my/{city}")
    public WeatherResponse getWeather(@PathVariable  String city) {
        return weatherService.getData(city);
    }

    @GetMapping("/forecast")
   public myweatherForcast getForecast(@RequestParam String city, @RequestParam int days) {
       return weatherService.getForecast(city, days);
    }

}
