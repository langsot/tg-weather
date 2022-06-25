package com.github.langsot.tgweather;

import com.github.langsot.tgweather.Entity.Status;
import com.github.langsot.tgweather.openWeather.Entity.WeatherEntity;
import com.github.langsot.tgweather.openWeather.controller.OpenWeatherRestTemplate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.*;

@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {
    @Value("${telegram.token}")
    private String token;
    @Value("${telegram.bot_name}")
    private String botName;

    private static final Map<Long, Status> CACHE = new HashMap<>();

    private final OpenWeatherRestTemplate weatherRestTemplate;

    public Bot(OpenWeatherRestTemplate weatherRestTemplate) {
        this.weatherRestTemplate = weatherRestTemplate;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }



    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Long id = update.getMessage().getChatId();


        if (update.getMessage().getText().equals("/start")) {
            KeyboardRow row = new KeyboardRow();
                row.add(KeyboardButton.builder().text("Отправить геолокацию").requestLocation(true).build());
                row.add(KeyboardButton.builder().text("Отправить город").build());

            List<KeyboardRow> rowList = new ArrayList<>();
                rowList.add(row);

            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                keyboardMarkup.setKeyboard(rowList);
                keyboardMarkup.setResizeKeyboard(true);

            execute(SendMessage.builder()
                    .chatId(id.toString())
                    .text("Что тебе надобно, пупсик?")
                    .replyMarkup(keyboardMarkup)
                    .build());

        } else if (update.getMessage().getText().equals("Отправить город")) {
            CACHE.put(id, Status.CHOSE_CITY);
            execute(SendMessage.builder()
                    .chatId(id.toString())
                    .text("Введите город")
                    .build());

        } else if (CACHE.containsKey(id)) {
            String city = update.getMessage().getText();
            try {
                log.info("Поиск города {}", city);
                WeatherEntity weather = weatherRestTemplate.getWeatherApi(city);
                execute(SendMessage.builder()
                        .chatId(id.toString())
                        .text(weather.toString())
                        .build());

            } catch (Exception e) {
                log.error("Ошибка в поиске по имени города");
                execute(SendMessage.builder()
                        .chatId(id.toString())
                        .text("Город " + city + " не найден")
                        .build());
            }
        }
    }
}
