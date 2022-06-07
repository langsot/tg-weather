package com.github.langsot.tgweather;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {
    private static final String TOKEN = "5337630732:AAGNYzRkXSDL6woOCWf8mvexB5yUdn2P_gE";
    private static final String BOT_NAME = "weather_tsk_bot";

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

        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                if (update.getMessage().getText().equals("Отправить город")) {
                    Integer id = update.getMessage().getForwardFromMessageId();
                }
            }
        }

        // Первая строка кнопок
        KeyboardRow row = new KeyboardRow();
            row.add(KeyboardButton.builder().text("Отправить геолокацию").requestLocation(true).build());
            row.add(KeyboardButton.builder().text("Отправить город").build());

        List<KeyboardRow> rowList = new ArrayList<>();
            rowList.add(row);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            keyboardMarkup.setKeyboard(rowList);
            keyboardMarkup.setResizeKeyboard(true);

        execute(SendMessage.builder().chatId(update.getMessage().getChatId().toString()).text("Что тебе надобно, пупсик?").replyMarkup(keyboardMarkup).build());
//        execute(SendMessage.builder().chatId(update.getMessage().getChatId().toString()).text("Ух, жараааа").build());

    }
}
