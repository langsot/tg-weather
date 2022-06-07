package com.github.langsot.tgweather.openWeather.controller;

import com.github.langsot.tgweather.openWeather.Entity.Weather;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@NoArgsConstructor
public class OpenWeatherRestTemplate {

    private RestTemplate restTemplate;

    private static final String OPEN_WEATHER_API = "http://api.openweathermap.org/data/2.5/weather?q={city}APPID=85aa52a555dac6ac36c4ecffbe15e13d&units=metric&lang=ru";


    public OpenWeatherRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Weather getWeatherApi(String cityName) {
        return restTemplate.getForObject(OPEN_WEATHER_API.replace("{city}", cityName), Weather.class);
    }
}
