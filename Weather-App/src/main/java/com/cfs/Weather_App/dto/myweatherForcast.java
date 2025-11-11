package com.cfs.Weather_App.dto;

import java.util.List;

public class myweatherForcast {
    private  WeatherResponse weatherResponse;

    private List<Daytamp> daytamp ;

    public myweatherForcast() {
    }

    public myweatherForcast(List<Daytamp> daytamp) {
        this.daytamp = daytamp;
    }

    public List<Daytamp> getDaytamp() {
        return daytamp;
    }

    public void setDaytamp(List<Daytamp> daytamp) {
        this.daytamp = daytamp;
    }

    public WeatherResponse getWeatherResponse() {
        return weatherResponse;
    }

    public void setWeatherResponse(WeatherResponse weatherResponse) {
        this.weatherResponse = weatherResponse;
    }
}
