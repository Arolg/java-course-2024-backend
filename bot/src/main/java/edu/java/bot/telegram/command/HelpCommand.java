package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.telegram.BotCommandList;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    private final String name = "/help";
    private final String description = "Вывести окно с командами";

    private final ApplicationContext context;

    @Autowired
    public HelpCommand(ApplicationContext context) {
        this.context = context;
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
        var commandHandlers = context.getBean(BotCommandList.class).getCommands();
        if (commandHandlers.isEmpty()) {
            return  new SendMessage(chatId, "*Список команд пустой!*");
        } else {
            return new SendMessage(chatId, "*Список команд:*\n\n" + commandHandlers.stream()
                .map((h) -> String.format("*%s* \\-\\> _%s_", h.command(), h.description()))
                .collect(Collectors.joining("\n")));
        }
    }
}
