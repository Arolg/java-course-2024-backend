package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface  Command {
    String command();

    String description();

    SendMessage handle(Update update);

    default boolean checkFormat(String command) {
        return command != null && command.equals(command());
    }
    default boolean supports(Update update)
    {
        return update.message().text() != null && update.message().text().equals(command());
    }

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
