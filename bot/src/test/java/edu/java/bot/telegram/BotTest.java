package edu.java.bot.telegram;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.BotApplication;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.telegram.command.Command;
import edu.java.bot.telegram.command.HelpCommand;
import edu.java.bot.telegram.command.ListCommand;
import edu.java.bot.telegram.command.StartCommand;
import edu.java.bot.telegram.command.TrackCommand;
import edu.java.bot.telegram.command.UntrackCommand;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BotTest {

    static Bot bot;
    static Update update;
    static Message message;
    static Chat chat;

    //static BotRepository repository = new BotRepository();
    static StartCommand startCommandHandler = new StartCommand(mock(BotRepository.class));
    static ListCommand listCommandHandler = new ListCommand(mock(BotRepository.class));
    static TrackCommand trackCommandHandler = new TrackCommand(mock(BotRepository.class));
    static UntrackCommand untrackCommandHandler = new UntrackCommand(mock(BotRepository.class));
    static List<Command> handlers = List.of(
    listCommandHandler,
    startCommandHandler,
    trackCommandHandler,
    untrackCommandHandler
        );
    static HelpCommand helpCommandHandler = new HelpCommand(handlers);


    @BeforeAll
    static void startBot() {
        update = mock(Update.class);
        message = mock(Message.class);
        chat = mock(Chat.class);
        when(update.message()).thenReturn(message);

        var ctx = SpringApplication.run(BotApplication.class);
        ApplicationConfig applicationConfig = ctx.getBean(ApplicationConfig.class);
        bot = ctx.getBean(Bot.class);
        bot.start();



    }


    @Test
    public void createMenuTest(){

        SetMyCommands actualRequest = bot.createMenu(handlers);

        SetMyCommands expectedRequest = new SetMyCommands(
            new BotCommand("/list", "Вывести список отслеживаемых ссылок"),
            new BotCommand("/start", "Зарегистрировать пользователя"),
            new BotCommand("/track", "Начать отслеживание ссылки"),
            new BotCommand("/untrack", "Прекратить отслеживание ссылки")
        );
        assertThat(actualRequest)
            .usingRecursiveComparison()
            .isEqualTo(expectedRequest);
    }

    @Test
    public void newUserTest() {
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/start");
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(chat.username()).thenReturn("TestUser");

        var actualAnswer = bot.handleUpdate(update);

        var expectedAnswer = new SendMessage(
            1L,
            "Привет, TestUser!\n\n"
                + "Я бот для отслеживания обновлений множества веб-ресурсов, которые тебе интересны! "
                + "Для получения списка доступных команд открой меню или введи /help.\n\n"
                + "Ты успешно зарегистрирован!"
                + " Можешь начинать отслеживать ссылки!"
        );
        assertThat(actualAnswer)
            .usingRecursiveComparison()
            .isEqualTo(expectedAnswer);
    }

    @Test
    public void olsUserTest() {
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/start");
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(chat.username()).thenReturn("TestUser");
        Map<Long, List<URL>> memory = new HashMap<>();;
        memory.put(1L, new ArrayList<>());
        BotRepository repository = new BotRepository(memory);
        StartCommand startCommand = new StartCommand(repository);
        SendMessage actualAnswer = startCommand.handle(update);

        var expectedAnswer = new SendMessage(
            1L,
            "Привет, TestUser!\n\n"
                + "Я бот для отслеживания обновлений множества веб-ресурсов, которые тебе интересны! "
                + "Для получения списка доступных команд открой меню или введи /help.\n\n"
                + "Ты уже был зарегистрирован!"
                + " Можешь начинать отслеживать ссылки!");
        AssertionsForClassTypes.assertThat(actualAnswer)
            .usingRecursiveComparison()
            .isEqualTo(expectedAnswer);
    }

    @Test
    public void unknownCommandHandleUpdateTest() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/chess");
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(2L);
        when(chat.username()).thenReturn("TestUser");

        var actualAnswer = bot.handleUpdate(update);

        var expectedAnswer = new SendMessage(
            2L, "Не знаю такой команды");
        assertThat(actualAnswer)
            .usingRecursiveComparison()
            .isEqualTo(expectedAnswer);
    }

    @Test
    void generateAnswerTest() {
        try {


            HelpCommand helpCommand = new HelpCommand(handlers);


            SendMessage response = helpCommand.handle(update);


            var expectedAnswer = "Список команд:\n\n"
                + "/list -> Вывести список отслеживаемых ссылок\n"
                + "/start -> Зарегистрировать пользователя\n"
                + "/track -> Начать отслеживание ссылки\n"
                + "/untrack -> Прекратить отслеживание ссылки";

            AssertionsForClassTypes.assertThat(response)
                .isEqualTo(expectedAnswer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
