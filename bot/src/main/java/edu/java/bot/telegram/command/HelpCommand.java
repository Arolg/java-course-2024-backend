package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    private final String name = "/help";
    private final String description = "Вывести окно с командами";
    //@Autowired
    @Qualifier("command")
    private final List<Command> commands;

    @Autowired
    public HelpCommand(List<Command> commands) {
        this.commands = commands;

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
        StringBuilder messageBuilder = new StringBuilder("Список команд:\n");
        //String username = update.message().chat().username();
        Long chatId = update.message().chat().id();
        //var commands =
        if (commands.isEmpty()) {
            return  new SendMessage(chatId, "*Список команд пустой!*");
        } else {
            return new SendMessage(chatId, "*Список команд:*\n\n" + commands.stream()
                .map((h) -> String.format("*%s* \\-\\> _%s_", h.command(), h.description()))
                .collect(Collectors.joining("\n")));
        }
    }
}
