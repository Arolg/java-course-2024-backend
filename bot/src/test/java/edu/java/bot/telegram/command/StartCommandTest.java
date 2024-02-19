package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StartCommandTest {
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
        when(message.text()).thenReturn("/start");
        when(chat.username()).thenReturn("testUser");

    }
    @Test
    void newUserTest() {
        StartCommand startCommand = new StartCommand();
        SendMessage response = startCommand.handle(update);
        var expectedAnswer = new SendMessage(1L, "Привет, *testUser*\\!\n\n"
            + "Я бот для ___отслеживания обновлений_\r__ множества веб\\-ресурсов, которые тебе интересны\\! "
            + "Для получения списка доступных команд открой ___меню_\r__ или введи /help\\.\n\n"
            + "Ты ___успешно_\r__ зарегистрирован\\! "
            + "Можешь начинать отслеживать ссылки\\!");
        assertThat(response)
            .usingRecursiveComparison()
            .isEqualTo(expectedAnswer);
    }

    @Test
    public void getCommandNameTest() {
        StartCommand startCommand = new StartCommand();

        String actualCommandName = startCommand.command();

        String expectedCommandName = "/start";
        assertThat(actualCommandName)
            .isEqualTo(expectedCommandName);
    }

    @Test
    public void getCommandDescriptionTest() {
        StartCommand startCommand = new StartCommand();

        String actualCommandDescription = startCommand.description();

        String expectedCommandDescription = "Зарегистрировать пользователя";
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
        StartCommand startCommand = new StartCommand();
        var badUpdate = mock(Update.class);
        var message = mock(Message.class);
        when(badUpdate.message()).thenReturn(message);
        when(message.text()).thenReturn(invalidCommand);

        assertThatThrownBy(() -> startCommand.handle(badUpdate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid command format!");
    }


}


