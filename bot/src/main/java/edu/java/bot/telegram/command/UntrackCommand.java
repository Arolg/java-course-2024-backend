package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements Command{
    private final String name = "/untrack";
    private final String description = "Прекратить отслеживание ссылки";

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

        return new SendMessage(chatId,
            "Прекратил отслеживание ссылки");


    }
}
