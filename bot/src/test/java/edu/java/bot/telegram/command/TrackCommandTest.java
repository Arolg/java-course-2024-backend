package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.telegram.BotRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrackCommandTest {
    static Update update;
    static Message message;
    static Chat chat;

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
    @Test
    void newUserTest() {
        var repository = new BotRepository();
        TrackCommand trackCommand = new TrackCommand(repository);
        SendMessage response = trackCommand.handle(update);
        var expectedAnswer = new SendMessage(1L, "Начал отслеживание ссылки");
        assertThat(response)
            .usingRecursiveComparison()
            .isEqualTo(expectedAnswer);
    }

    @Test
    public void getCommandNameTest() {
        var repository = new BotRepository();
        TrackCommand trackCommand = new TrackCommand(repository);

        String actualCommandName = trackCommand.command();

        String expectedCommandName = "/track";
        assertThat(actualCommandName)
            .isEqualTo(expectedCommandName);
    }

    @Test
    public void getCommandDescriptionTest() {
        var repository = new BotRepository();
        TrackCommand trackCommand = new TrackCommand(repository);

        String actualCommandDescription = trackCommand.description();

        String expectedCommandDescription = "Начать отслеживание ссылки";
        assertThat(actualCommandDescription)
            .isEqualTo(expectedCommandDescription);
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

