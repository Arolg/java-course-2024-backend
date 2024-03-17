package edu.java.bot.telegram.command;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UntrackCommandTest {
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
        when(message.text()).thenReturn("/untrack");
        when(chat.username()).thenReturn("testUser");

    }

    @BeforeEach
    void clear(){
        memory.clear();
        repository = new BotRepository(memory);
    }
    @Test
    void newUserTest() {
        UntrackCommand untrackCommand = new UntrackCommand(repository);
        SendMessage response = untrackCommand.handle(update);
        var expectedAnswer = new SendMessage(1L,
            "Не указан URL для прекращения отслеживания.Напишите её через пробел после команды /track!");
        assertThat(response)
            .usingRecursiveComparison()
            .isEqualTo(expectedAnswer);
    }

    @Test
    public void getCommandNameTest() {
        UntrackCommand untrackCommand = new UntrackCommand(repository);

        String actualCommandName = untrackCommand.command();

        String expectedCommandName = "/untrack";
        assertThat(actualCommandName)
            .isEqualTo(expectedCommandName);
    }

    @Test
    public void getCommandDescriptionTest() {
        UntrackCommand untrackCommand = new UntrackCommand(repository);

        String actualCommandDescription = untrackCommand.description();

        String expectedCommandDescription = "Прекратить отслеживание ссылки";
        assertThat(actualCommandDescription)
            .isEqualTo(expectedCommandDescription);
    }

    @Test
    public void successfulLinkCommandTest() {
        try {
            URL successfulUrl = new URI("https://stackoverflow.com/question/1234").toURL();
            memory.put(1L, List.of(successfulUrl));
            repository.trackLink(1L,successfulUrl);
            Update update = mock(Update.class);
            Message message = mock(Message.class);
            Chat chat = mock(Chat.class);
            when(update.message()).thenReturn(message);
            when(message.chat()).thenReturn(chat);
            when(message.chat().id()).thenReturn(1L);
            when(message.text()).thenReturn("/untrack " + successfulUrl);
            UntrackCommand untrackCommand = new UntrackCommand(repository);
            SendMessage response = untrackCommand.handle(update);

            var expectedAnswer = new SendMessage(1L,
                "Ссылка успешно удалена");
            assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(expectedAnswer);
        } catch (IllegalArgumentException | MalformedURLException | URISyntaxException ex) {
            fail("Runtime Exception while making urls from strings: " + ex.getMessage());
        }
    }
    @ParameterizedTest
    @CsvSource({
        "/untrack *-+quillbot.com*grammar-check",
        "/untrack httfinance.yahoo.com/q/h?s=%5EIXIC",
        "/untrack withspace"
    })
    public void invalidFormatTest(String invalidCommand) {
        UntrackCommand untrackCommand = new UntrackCommand(repository);
        var badUpdate = mock(Update.class);
        var message = mock(Message.class);
        when(badUpdate.message()).thenReturn(message);
        when(message.text()).thenReturn(invalidCommand);
        when(message.chat()).thenReturn(chat);

        SendMessage response = untrackCommand.handle(badUpdate);
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
        UntrackCommand untrackCommand = new UntrackCommand(repository);
        var badUpdate = mock(Update.class);
        var message = mock(Message.class);
        when(badUpdate.message()).thenReturn(message);
        when(message.text()).thenReturn(invalidCommand);

        assertThatThrownBy(() -> untrackCommand.handle(badUpdate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid command format!");
    }


}

