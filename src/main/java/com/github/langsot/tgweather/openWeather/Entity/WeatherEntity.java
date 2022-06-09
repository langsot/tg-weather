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
    private Wind wind;

    @Setter
    @Getter
    private static class Main {
        private Double temp;
        private Double feels_like;
    }

    @Getter
    @Setter
    private static class Weather {
        private String description;
        private String icon;
    }

    @Setter
    @Getter
    private static class Wind {
        private Double speed;
    }

    public String getCurrentSmile(String description) {
        return switch(description) {
            case "01d" -> "☀";
            case "02d", "02n" -> "\uD83C\uDF24";
            case "03d", "03n" -> "⛅";
            case "04d", "04n" -> "\uD83C\uDF25";
            case "05d", "05n" -> "\uD83C\uDF27";
            case "06d", "06n" -> "\uD83C\uDF26";
            case "07d", "07n" -> "⛈";
            case "08d", "08n" -> "❄";
            case "09d", "09n" -> "\uD83C\uDF2B";
            case "01n" -> "\uD83C\uDF19";
            default -> "";
        };
    }

    public String getCurrentWind(Double speed) {
        if (speed < 0.5) {
            return "Штиль";
        } else if (speed < 1.7) {
            return "Тихий ветерок";
        } else if (speed < 4.5) {
            return "Легкий ветер";
        } else if (speed < 10) {
            return "Умеренный ветер";
        } else if (speed < 14.2) {
            return "Сильный ветер";
        } else if (speed < 18.2) {
            return "Очень сильный ветер";
        } else if (speed < 21.5) {
            return "Штормовой ветер";
        } else return "Ураган";
    }

    public String toString() {
        return name + " - "
                + getCurrentSmile(weather.get(0).getIcon())
                + " " + weather.get(0).getDescription()
                + String.format(
                        "\n\uD83C\uDF21Температура: %.1f°C  (feel - %.1f)" +
                                "\n\uD83C\uDF2A %s: %.1f м/c" , main.temp, main.feels_like, getCurrentWind(wind.speed), wind.speed);
    }


}
