package com.github.langsot.tgweather.openWeather.controller;

import com.github.langsot.tgweather.openWeather.Entity.WeatherEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenWeatherRestTemplate {

    private static final String OPEN_WEATHER_API = "http://api.openweathermap.org/data/2.5/weather?q={city}&APPID=85aa52a555dac6ac36c4ecffbe15e13d&units=metric&lang=ru";

    private RestTemplate restTemplate;

    public OpenWeatherRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public WeatherEntity getWeatherApi(String cityName) {
        String url = OPEN_WEATHER_API.replace("{city}", cityName);

        System.out.println(restTemplate.getForObject(url, WeatherEntity.class));
        return restTemplate.getForObject(url, WeatherEntity.class);
    }
}
