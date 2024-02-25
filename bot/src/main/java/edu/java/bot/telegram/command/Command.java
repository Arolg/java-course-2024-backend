package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public interface  Command {
    String command();

    String description();

    SendMessage handle(Update update);

    default boolean supports(Update update) {
        return command().equals(update.message().text());
    }

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
