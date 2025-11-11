package com.cfs.Weather_App.Service;

import com.cfs.Weather_App.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {
// kuch varible lene ke lie ham property file se varible ko read karane ke lie @value lete hai

    @Value("${weather.api.key}")
    public String apiKey;



    @Value("${weather.api.url}")
    public String apiUrl;



    @Value("${weather.api.forecast.url}")
    public String apiUrlForcast;



    private RestTemplate restTemplate= new RestTemplate();
    public String test() {

        return  "good";
    }

    public WeatherResponse getData(String city) {
        String url = apiUrl + "?key=" + apiKey + "&q=" + city;
        Root response = restTemplate.getForObject(url, Root.class);


        WeatherResponse weatherResponse = new WeatherResponse();

        weatherResponse.setCity(response.getLocation().name);
        weatherResponse.setRegion(response.getLocation().region);
        weatherResponse.setCountry(response.getLocation().country);
        String Condition = response.getCurrent().getCondition().text;
        weatherResponse.setCondition(Condition);
        weatherResponse.setTemperature(response.getCurrent().temp_c);

        return weatherResponse;
    }
    public myweatherForcast getForecast(String city, int days) {
        myweatherForcast myweatherForcast = new myweatherForcast();
        WeatherResponse weatherResponse = getData(city);
        myweatherForcast.setWeatherResponse(weatherResponse);

        List<Daytamp> dayList = new ArrayList<>();

        String url = apiUrlForcast + "?key=" + apiKey + "&q=" + city + "&days=" + days;
        Root apiresponse = restTemplate.getForObject(url, Root.class);

        if (apiresponse != null && apiresponse.getForecast() != null) {
            Forecast forecast = apiresponse.getForecast();
            ArrayList<Forecastday> forecastdays = forecast.getForecastday();

            for (Forecastday rs : forecastdays) {
                Daytamp d = new Daytamp();
                d.setDate(rs.getDate());
                d.setMaxtemp(rs.getDay().getMaxtemp_c());
                d.setMintemp(rs.getDay().getMintemp_c());
                d.setAvgtamp(rs.getDay().getAvgtemp_c());
                dayList.add(d);
            }
        }

        // ✅ correct place to set the list
        myweatherForcast.setDaytamp(dayList);

        return myweatherForcast;
    }






}





