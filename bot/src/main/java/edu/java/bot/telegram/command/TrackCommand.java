package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.telegram.BotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Component
public class TrackCommand implements Command {
    private final String name = "/track";
    private final String description = "Начать отслеживание ссылки";
    private final BotRepository repository;
    //@Autowired
    public TrackCommand(BotRepository repository) {
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
    public boolean supports(Update update) {
        String command = update.message().text();
        return command != null && (command().equals(command) || command.startsWith(name + " "));
    }


    @Override
    public SendMessage handle(Update update) {
        String messageText = update.message().text();
        if (!supports(update)) {
            throw new IllegalArgumentException("Invalid command format!");
        }

        String[] parts = messageText.split("\\s+", 2);
        Long chatId = update.message().chat().id();

        if (parts.length != 2) {
            return new SendMessage(chatId,
                "Не указан URL для отслеживания\\. Напишите её через пробел после команды /track\\!");
        }
        URL url;
        try {
            url = new URI(parts[1]).toURL();
        } catch (IllegalArgumentException | MalformedURLException | URISyntaxException ex){
            return new SendMessage(chatId, "Неверный формат URL\\. Попробуйте снова");
        }

        repository.trackLink(chatId, url);
        return new SendMessage(chatId, "Ссылка добавлена для отслеживания");

    }

}
