package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
    private final String name = "/start";
    private final String description = "Зарегистрировать пользователя";

    @Override
    public String command() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public SendMessage handle(Update update) {
        if (!supports(update)) {
            throw new IllegalArgumentException("Invalid command format!");
        }
        String username = update.message().chat().username();
        Long chatId = update.message().chat().id();
        String welcomeMessage = String.format(
            "Привет, *%s*\\!\n\n"
                + "Я бот для ___отслеживания обновлений_\r__ множества веб\\-ресурсов, которые тебе интересны\\! "
                + "Для получения списка доступных команд открой ___меню_\r__ или введи /help\\.\n\n",
            username
        );

        return new SendMessage(chatId, welcomeMessage
                + "Ты ___успешно_\r__ зарегистрирован\\! "
                + "Можешь начинать отслеживать ссылки\\!");

    }
}
