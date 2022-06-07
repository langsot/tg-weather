package com.github.langsot.tgweather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@SpringBootApplication
public class TgWeatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(TgWeatherApplication.class, args);
    }



}