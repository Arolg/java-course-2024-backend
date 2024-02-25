package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.ArrayList;
import java.util.List;
import edu.java.bot.telegram.BotRepository;
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



    @BeforeAll
    static void generateAnswerTest() {
        try {
            update = mock(Update.class);
            message = mock(Message.class);
            chat = mock(Chat.class);

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
        var repository = new BotRepository();
        var startCommandHandler = new StartCommand();
        var listCommandHandler = new ListCommand(repository);
        var trackCommandHandler = new TrackCommand(repository);
        var untrackCommandHandler = new UntrackCommand(repository);
        var handlers = List.of(
            listCommandHandler,
            startCommandHandler,
            trackCommandHandler,
            untrackCommandHandler
        );
        var helpCommand = new HelpCommand(handlers);
        when(update.message().text()).thenReturn("/help");

        SendMessage response = helpCommand.handle(update);
        var expectedAnswer = new SendMessage(
            1L,
            "Список команд:\n\n"
                + "/list -> Вывести список отслеживаемых ссылок\n"
                + "/start -> Зарегистрировать пользователя\n"
                + "/track -> Начать отслеживание ссылки\n"
                + "/untrack -> Прекратить отслеживание ссылки");



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
        var repository = new BotRepository();
        var startCommandHandler = new StartCommand();
        var listCommandHandler = new ListCommand(repository);
        var trackCommandHandler = new TrackCommand(repository);
        var untrackCommandHandler = new UntrackCommand(repository);
        var handlers = List.of(
            listCommandHandler,
            startCommandHandler,
            trackCommandHandler,
            untrackCommandHandler
        );
        var helpCommandHandler = new HelpCommand(handlers);
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
        List<Command> emptyContext = new ArrayList<>();
        var helpCommandHandler = new HelpCommand(emptyContext);

        var actualAnswer = helpCommandHandler.handle(update);

        var expectedAnswer = new SendMessage(
            1L,"Список команд пустой!");
        assertThat(actualAnswer)
            .usingRecursiveComparison()
            .isEqualTo(expectedAnswer);
    }

    @Test
    public void getCommandNameTest() {
        var repository = new BotRepository();
        var startCommandHandler = new StartCommand();
        var listCommandHandler = new ListCommand(repository);
        var trackCommandHandler = new TrackCommand(repository);
        var untrackCommandHandler = new UntrackCommand(repository);
        var handlers = List.of(
            listCommandHandler,
            startCommandHandler,
            trackCommandHandler,
            untrackCommandHandler
        );
        var helpCommandHandler = new HelpCommand(handlers);

        String actualCommandName = helpCommandHandler.command();

        String expectedCommandName = "/help";
        assertThat(actualCommandName)
            .isEqualTo(expectedCommandName);
    }

    @Test
    public void getCommandDescriptionTest() {
        var repository = new BotRepository();
        var startCommandHandler = new StartCommand();
        var listCommandHandler = new ListCommand(repository);
        var trackCommandHandler = new TrackCommand(repository);
        var untrackCommandHandler = new UntrackCommand(repository);
        var handlers = List.of(
            listCommandHandler,
            startCommandHandler,
            trackCommandHandler,
            untrackCommandHandler
        );
        var helpCommandHandler = new HelpCommand(handlers);

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
        var repository = new BotRepository();
        var startCommandHandler = new StartCommand();
        var listCommandHandler = new ListCommand(repository);
        var trackCommandHandler = new TrackCommand(repository);
        var untrackCommandHandler = new UntrackCommand(repository);
        var handlers = List.of(
            listCommandHandler,
            startCommandHandler,
            trackCommandHandler,
            untrackCommandHandler
        );
        var helpCommandHandler = new HelpCommand(handlers);
        when(update.message().text()).thenReturn(command);

        boolean actualCheckResult = helpCommandHandler.supports(update);


        assertThat(actualCheckResult)
            .isEqualTo(expectedCheckResult);
    }
}
