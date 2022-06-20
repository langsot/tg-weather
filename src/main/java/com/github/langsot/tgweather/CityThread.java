package com.github.langsot.tgweather;

import com.github.langsot.tgweather.openWeather.Entity.WeatherEntity;
import com.github.langsot.tgweather.openWeather.controller.OpenWeatherRestTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@EnableScheduling
@AllArgsConstructor
@Slf4j
public class CityThread {

    private DefaultAbsSender sender;
    private OpenWeatherRestTemplate restTemplate;


    @Scheduled(fixedDelay = 2000)
    public synchronized void sender() throws TelegramApiException {
        if (!Bot.CACHE_CITY.isEmpty()) {
            Responce id;

//            synchronized (Bot.CACHE_CITY) {
                id = Bot.CACHE_CITY.first();
                Bot.CACHE_CITY.remove(Bot.CACHE_CITY.first());
                log.info("удаляю город {}", id.getCity());
//            }

            try {
//                log.info("Поиск города {}", id.getCity());
                WeatherEntity weather = restTemplate.getWeatherApi(id.getCity());
                sender.execute(SendMessage.builder()
                        .chatId(id.getChatId().toString())
                        .text(weather.toString())
                        .build());

            } catch (Exception e) {
//                log.error("Ошибка в поиске по имени города");
                sender.execute(SendMessage.builder()
                        .chatId(id.getChatId().toString())
                        .text("Город " + id.getCity() + " не найден")
                        .build());
            }
        }

    }
}
