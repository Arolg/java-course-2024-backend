package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.telegram.BotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
    private final String name = "/start";
    private final String description = "Зарегистрировать пользователя";
    private final BotRepository repository;

    @Autowired
    public StartCommand(BotRepository repository) {
        this.repository = repository;
    }

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
            "Привет, %s!\n\n"
                + "Я бот для отслеживания обновлений множества веб-ресурсов, которые тебе интересны! "
                + "Для получения списка доступных команд открой меню или введи /help.\n\n",
            username
        );

        if (repository.addUser(chatId)) {
            return new SendMessage(chatId, welcomeMessage
                + "Ты успешно зарегистрирован! "
                + "Можешь начинать отслеживать ссылки!");
        } else {

            return new SendMessage(chatId, welcomeMessage
                + "Ты уже был зарегистрирован!");

        }


    }
}
