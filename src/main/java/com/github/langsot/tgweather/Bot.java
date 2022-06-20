package com.github.langsot.tgweather;

import com.github.langsot.tgweather.Entity.Status;
import com.github.langsot.tgweather.openWeather.controller.OpenWeatherRestTemplate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
    private static final String TOKEN = "5337630732:AAGNYzRkXSDL6woOCWf8mvexB5yUdn2P_gE";
    private static final String BOT_NAME = "weather_tsk_bot";

    private static final Map<Long, Status> CACHE = new HashMap<>();
    public static final TreeSet<Responce> CACHE_CITY = new TreeSet<>();

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
            Responce responce = Responce.builder().chatId(id).city(update.getMessage().getText()).time(new Random().nextInt(10)).build();
            log.info("Добавляю город {}", responce.getCity());
            CACHE_CITY.add(responce);
            execute(SendMessage.builder()
                    .chatId(id.toString())
                    .text("Поиск погоды для - " + update.getMessage().getText())
                    .build());
        }
    }
}
