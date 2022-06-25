package com.github.langsot.tgweather.openWeather.controller;

import com.github.langsot.tgweather.openWeather.Entity.WeatherEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenWeatherRestTemplate {

    @Value("${weather.url.city}")
    private String urlCity;

    @Value("${weather.url.location}")
    private String urlLocation;

    private final RestTemplate restTemplate;

    public OpenWeatherRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public WeatherEntity getWeatherApiCity(String cityName) {
        String url = urlCity.replace("{city}", cityName);

//        System.out.println(restTemplate.getForObject(url, WeatherEntity.class));
        return restTemplate.getForObject(url, WeatherEntity.class);
    }

    public WeatherEntity getWeatherApiLocation(Double lat, Double lon) {
        String url = urlLocation.replace("{lat}", lat.toString()).replace("{lon}", lon.toString());

//        System.out.println(restTemplate.getForObject(url, WeatherEntity.class));
        return restTemplate.getForObject(url, WeatherEntity.class);
    }
}
