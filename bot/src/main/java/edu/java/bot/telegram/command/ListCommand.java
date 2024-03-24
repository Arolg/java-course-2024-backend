package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.telegram.BotRepository;
import java.net.URL;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {
    private final String name = "/list";
    private final String description = "Вывести список отслеживаемых ссылок";
    private final BotRepository repository;

    @Autowired
    public ListCommand(BotRepository repository) {
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
        Long chatId = update.message().chat().id();
        List<URL> trackedLinks = repository.listLink(chatId);
        if (trackedLinks.isEmpty()) {
            return new SendMessage(chatId,
                "Список отслеживаемых ссылок пуст");
        } else {
            StringBuilder message = new StringBuilder("Список отслеживаемях ссылок\n");
            for (var link : trackedLinks) {
                message.append(link).append("\n");
            }
            return new SendMessage(chatId, message.toString());
        }

    }
}
