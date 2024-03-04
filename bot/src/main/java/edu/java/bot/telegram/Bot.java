package edu.java.bot.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.telegram.command.Command;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Bot implements AutoCloseable, UpdatesListener {
    private final String token;
    private TelegramBot bot;

    private final List<Command> commandHandlers;
    //private final BotRepository botRepository;

    public Bot(ApplicationConfig config, List<Command> commandList) {

        this.token = config.telegramToken();
        //this.botRepository = new BotRepository();
        this.bot = new TelegramBot(token);
        this.commandHandlers = commandList;
        bot.execute(createMenu(commandHandlers));
    }


    public SetMyCommands createMenu(List<Command> handlers) {
        return new SetMyCommands(
            handlers.stream()
                .map((c) -> new BotCommand(c.command(), c.description()))
                .toArray(BotCommand[]::new)
        );
    }

    <T extends BaseRequest<T, R>, R extends BaseResponse> BaseResponse execute(BaseRequest<T, R> request) {
        return bot.execute(request);
    }


    public void start() {
        bot.setUpdatesListener(updates -> {
            process(updates);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }


    @Override
    public int process(List<Update> updates) {
        int processedUpdates = 0;
        for (Update update : updates) {
            SendMessage response = handleUpdate(update);
            if (response != null) {
                BaseResponse baseResponse = execute(response);
                if (baseResponse.isOk()) {
                    processedUpdates++;
                }
            }
        }
        return processedUpdates;

    }

    @Override
    public void close() throws Exception {
        bot.shutdown();
    }

    public SendMessage handleUpdate(Update update) {
        Command command = null;
        for (var h : commandHandlers) {
            if (h.supports(update)) {
                command = h;
            }
        }
        if (command != null) {
            return command.handle(update);
        } else {
            return new SendMessage(
                update.message().chat().id(),
                "Не знаю такой команды"
            );
        }
    }

}
