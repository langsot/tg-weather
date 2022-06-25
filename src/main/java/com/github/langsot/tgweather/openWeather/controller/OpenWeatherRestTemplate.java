package com.github.langsot.tgweather.openWeather.controller;

import com.github.langsot.tgweather.openWeather.Entity.WeatherEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenWeatherRestTemplate {

    @Value("${open.weather.api}")
    private String weatherSuite;

    private RestTemplate restTemplate;

    public OpenWeatherRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public WeatherEntity getWeatherApi(String cityName) {
        String url = weatherSuite.replace("{city}", cityName);

//        System.out.println(restTemplate.getForObject(url, WeatherEntity.class));
        return restTemplate.getForObject(url, WeatherEntity.class);
    }
}
