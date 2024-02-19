package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.telegram.BotCommandList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.context.ApplicationContext;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HelpCommandTest {
    static Update update;
    static Message message;
    static Chat chat;
    static ApplicationContext context;


    @BeforeAll
    static void generateAnswerTest() {
        try {

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

            update = mock(Update.class);
            message = mock(Message.class);
            chat = mock(Chat.class);
            context = mock(ApplicationContext.class);

            when(context.getBean(BotCommandList.class)).thenReturn(commands);
            when(update.message()).thenReturn(message);
            when(update.message().chat()).thenReturn(chat);
            when(chat.id()).thenReturn(1L);
            when(message.text()).thenReturn("/help");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void allCommandsTest() {
        when(update.message().text()).thenReturn("/help");
        HelpCommand helpCommand = new HelpCommand(context);
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

    }

    @ParameterizedTest
    @CsvSource({
        "invalid",
        "/helpwithoutspace",
        "/start withspace",
        "/heal",
        "help",
        "\"\""
    })
    public void invalidCommandTest(String invalidCommand) {
        var helpCommandHandler = new HelpCommand(context);
        var badUpdate = mock(Update.class);
        var message = mock(Message.class);
        when(badUpdate.message()).thenReturn(message);
        when(message.text()).thenReturn(invalidCommand);

        assertThatThrownBy(() -> helpCommandHandler.handle(badUpdate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid command format!");
    }



    @Test
    public void emptyCommandsTest() {
        var emptyContext = mock(ApplicationContext.class);
        BotCommandList emptyCommands = mock(BotCommandList.class);
        when(emptyContext.getBean(BotCommandList.class)).thenReturn(emptyCommands);
        when(emptyCommands.getCommands()).thenReturn(List.of());
        var helpCommandHandler = new HelpCommand(emptyContext);

        var actualAnswer = helpCommandHandler.handle(update);

        var expectedAnswer = new SendMessage(
            1L,"*Список команд пустой!*");
        assertThat(actualAnswer)
            .usingRecursiveComparison()
            .isEqualTo(expectedAnswer);
    }

    @Test
    public void getCommandNameTest() {
        var helpCommandHandler = new HelpCommand(context);

        String actualCommandName = helpCommandHandler.command();

        String expectedCommandName = "/help";
        assertThat(actualCommandName)
            .isEqualTo(expectedCommandName);
    }

    @Test
    public void getCommandDescriptionTest() {
        var helpCommandHandler = new HelpCommand(context);

        String actualCommandDescription = helpCommandHandler.description();

        String expectedCommandDescription = "Вывести окно с командами";
        assertThat(actualCommandDescription)
            .isEqualTo(expectedCommandDescription);
    }

    @ParameterizedTest
    @CsvSource({
        "/help, true",
        "/help withspace, false",
        "/heal, false",
        "help, false",
        " , false"
    })
    public void checkFormatTest(String command, boolean expectedCheckResult) {
        var helpCommandHandler = new HelpCommand(context);
        when(update.message().text()).thenReturn(command);

        boolean actualCheckResult = helpCommandHandler.supports(update);


        assertThat(actualCheckResult)
            .isEqualTo(expectedCheckResult);
    }
}
