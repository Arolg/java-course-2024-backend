package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.telegram.BotCommandList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import java.lang.reflect.Method;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class HelpCommandTest {
    @Test
    void generateAnswerTest() {
        try {
            ApplicationContext context = mock(ApplicationContext.class);
            var startCommandHandler = new StartCommand();
            var helpCommandHandler = new HelpCommand(mock(ApplicationContext.class));
            var listCommandHandler = new ListCommand();
            var trackCommandHandler = new TrackCommand();
            var untrackCommandHandler = new UntrackCommand();
            BotCommandList commands = new BotCommandList(
                startCommandHandler,
                helpCommandHandler,
                trackCommandHandler,
                untrackCommandHandler,
                listCommandHandler
            );

            Update update = mock(Update.class);
            Message message = mock(Message.class);
            Chat chat = mock(Chat.class);

            HelpCommand helpCommand = new HelpCommand(context);
            when(context.getBean(BotCommandList.class)).thenReturn(commands);
            when(update.message()).thenReturn(message);
            when(update.message().chat()).thenReturn(chat);
            when(chat.id()).thenReturn(1L);
            when(message.text()).thenReturn("/help");

            SendMessage response = helpCommand.handle(update);


            var expectedAnswer = new SendMessage(
                1L,
                "*Список команд:*\n\n"
                    + "*/start* \\-\\> _Зарегистрировать пользователя_\n"
                    + "*/help* \\-\\> _Вывести окно с командами_\n"
                    + "*/track* \\-\\> _Начать отслеживание ссылки_\n"
                    + "*/untrack* \\-\\> _Прекратить отслеживание ссылки_\n"
                    + "*/list* \\-\\> _Вывести список отслеживаемых ссылок_");

            assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(expectedAnswer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
