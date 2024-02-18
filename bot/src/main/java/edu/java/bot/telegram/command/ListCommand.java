package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {
    private final String name = "/list";
    private final String description = "Вывести список отслеживаемых ссылок";

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
        List<String> trackedLinks = new ArrayList<>();
        String username = update.message().chat().username();
        Long chatId = update.message().chat().id();
        if (trackedLinks.isEmpty()) {
            return new SendMessage(chatId,
                "Список отслеживаемых ссылок пуст");
        } else {
            StringBuilder message = new StringBuilder("Список отслеживаемях ссылок");
            for (String link : trackedLinks) {
                message.append(link);
            }
            return new SendMessage(chatId, message.toString());
        }

    }
}
