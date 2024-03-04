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
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StartCommandTest {
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
        when(message.text()).thenReturn("/start");
        when(chat.username()).thenReturn("testUser");

    }
    @BeforeEach
    void clear(){
        memory.clear();
        repository = new BotRepository(memory);
    }

    @Test
    public void getCommandNameTest() {
        StartCommand startCommand = new StartCommand(mock(BotRepository.class));

        String actualCommandName = startCommand.command();

        String expectedCommandName = "/start";
        assertThat(actualCommandName)
            .isEqualTo(expectedCommandName);
    }


    @Test
    public void getCommandDescriptionTest() {
        StartCommand startCommand = new StartCommand(mock(BotRepository.class));

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
        StartCommand startCommand = new StartCommand(mock(BotRepository.class));
        var badUpdate = mock(Update.class);
        var message = mock(Message.class);
        when(badUpdate.message()).thenReturn(message);
        when(message.text()).thenReturn(invalidCommand);

        assertThatThrownBy(() -> startCommand.handle(badUpdate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid command format!");
    }


}


