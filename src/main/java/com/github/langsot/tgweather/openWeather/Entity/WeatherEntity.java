package com.github.langsot.tgweather.openWeather.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherEntity {

    private String name;
    private List<Weather> weather;
    private Main main;

    public String getCurrentSmile(String description) {
        return switch(description) {
            case "01d" -> "☀";
            case "02d" -> "\uD83C\uDF24";
            case "03d" -> "⛅";
            case "04d" -> "\uD83C\uDF25";
            case "05d" -> "\uD83C\uDF27";
            case "06d" -> "\uD83C\uDF26";
            case "07d" -> "⛈";
            case "08d" -> "❄";
            case "09d" -> "\uD83C\uDF2B";
            default -> "";
        };
    }

    public String toString() {
        return name + " - "
                + weather.get(0).getDescription() + " "
                + getCurrentSmile(weather.get(0).getIcon())
                + "\n\uD83C\uDF21Температура: " + main.temp
                + "\nОщущается как: " + main.feels_like;
    }

    @Setter
    @Getter
    private class Main {
        private String temp;
        private String feels_like;
    }

    @Getter
    @Setter
    private class Weather {
        private String description;
        private String icon;
    }
}
