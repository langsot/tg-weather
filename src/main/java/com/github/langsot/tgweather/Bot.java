package com.github.langsot.tgweather;

import com.github.langsot.tgweather.Entity.Status;
import com.github.langsot.tgweather.openWeather.Entity.WeatherEntity;
import com.github.langsot.tgweather.openWeather.controller.OpenWeatherRestTemplate;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Bot extends TelegramLongPollingBot {
    private static final String TOKEN = "5337630732:AAGNYzRkXSDL6woOCWf8mvexB5yUdn2P_gE";
    private static final String BOT_NAME = "weather_tsk_bot";

    private static final Map<Long, Status> CACHE = new HashMap<>();

    private OpenWeatherRestTemplate weatherRestTemplate;

    public Bot(OpenWeatherRestTemplate weatherRestTemplate) {
        this.weatherRestTemplate = weatherRestTemplate;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        if (update.getMessage().getText().equals("/start")) {
            KeyboardRow row = new KeyboardRow();
                row.add(KeyboardButton.builder().text("Отправить геолокацию").requestLocation(true).build());
                row.add(KeyboardButton.builder().text("Отправить город").build());

            List<KeyboardRow> rowList = new ArrayList<>();
                rowList.add(row);

            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                keyboardMarkup.setKeyboard(rowList);
                keyboardMarkup.setResizeKeyboard(true);

            execute(SendMessage.builder().chatId(update.getMessage().getChatId().toString()).text("Что тебе надобно, пупсик?").replyMarkup(keyboardMarkup).build());
        } else if (update.getMessage().getText().equals("Отправить город")) {
            CACHE.put(update.getMessage().getChatId(), Status.CHOSE_CITY);
            execute(SendMessage.builder().chatId(update.getMessage().getChatId().toString()).text("Введите город").build());
        } else if (CACHE.containsKey(update.getMessage().getChatId())) {
            WeatherEntity weather = weatherRestTemplate.getWeatherApi(update.getMessage().getText());
            execute(SendMessage.builder().chatId(update.getMessage().getChatId().toString()).text(weather.toString()).build());
        }


    }
}
