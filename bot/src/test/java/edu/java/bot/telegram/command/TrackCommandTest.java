package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.telegram.BotRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrackCommandTest {
    static Update update;
    static Message message;
    static Chat chat;
    static Map<Long, List<URL>> memory = new HashMap<>();;
    static BotRepository repository;

    @BeforeAll
    static void generateAnswerTest() {
        update = mock(Update.class);
        message = mock(Message.class);
        chat = mock(Chat.class);


        when(update.message()).thenReturn(message);
        when(update.message().chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(message.text()).thenReturn("/track");
        when(chat.username()).thenReturn("testUser");

    }
    @BeforeEach
    void clear(){
        memory.clear();
        repository = new BotRepository(memory);
    }
    @Test
    void newUserTest() {
        TrackCommand trackCommand = new TrackCommand(repository);
        SendMessage response = trackCommand.handle(update);
        var expectedAnswer = new SendMessage(1L,
            "Не указан URL для отслеживания. Напишите её через пробел после команды /track!");
        assertThat(response)
            .usingRecursiveComparison()
            .isEqualTo(expectedAnswer);
    }

    @Test
    public void getCommandNameTest() {
        TrackCommand trackCommand = new TrackCommand(repository);

        String actualCommandName = trackCommand.command();

        String expectedCommandName = "/track";
        assertThat(actualCommandName)
            .isEqualTo(expectedCommandName);
    }

    @Test
    public void getCommandDescriptionTest() {
        TrackCommand trackCommand = new TrackCommand(repository);

        String actualCommandDescription = trackCommand.description();

        String expectedCommandDescription = "Начать отслеживание ссылки";
        assertThat(actualCommandDescription)
            .isEqualTo(expectedCommandDescription);
    }

    @Test
    public void successfulLinkCommandTest() {
        try {
            URL successfulUrl = new URI("https://stackoverflow.com/question/1234").toURL();
            memory.put(1L, List.of());
            Update update = mock(Update.class);
            Message message = mock(Message.class);
            Chat chat = mock(Chat.class);
            when(update.message()).thenReturn(message);
            when(message.chat()).thenReturn(chat);
            when(message.chat().id()).thenReturn(1L);
            when(message.text()).thenReturn("/track " + successfulUrl);
            TrackCommand trackCommand = new TrackCommand(repository);
            SendMessage response = trackCommand.handle(update);

            var expectedAnswer = new SendMessage(1L,
                "Ссылка добавлена для отслеживания");
            assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(expectedAnswer);
        } catch (IllegalArgumentException | MalformedURLException | URISyntaxException ex) {
            fail("Runtime Exception while making urls from strings: " + ex.getMessage());
        }
    }
    @ParameterizedTest
    @CsvSource({
        "/track *-+quillbot.com*grammar-check",
        "/track httfinance.yahoo.com/q/h?s=%5EIXIC",
        "/track withspace"
    })
    public void invalidFormatTest(String invalidCommand) {
        TrackCommand trackCommand = new TrackCommand(repository);
        var badUpdate = mock(Update.class);
        var message = mock(Message.class);
        when(badUpdate.message()).thenReturn(message);
        when(message.text()).thenReturn(invalidCommand);
        when(message.chat()).thenReturn(chat);

        SendMessage response = trackCommand.handle(badUpdate);
        var expectedAnswer = new SendMessage(1L,
            "Неверный формат URL. Попробуйте снова");
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
        TrackCommand trackCommand = new TrackCommand(repository);
        var badUpdate = mock(Update.class);
        var message = mock(Message.class);
        when(badUpdate.message()).thenReturn(message);
        when(message.text()).thenReturn(invalidCommand);

        assertThatThrownBy(() -> trackCommand.handle(badUpdate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid command format!");
    }


}

