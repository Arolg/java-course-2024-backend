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
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BotTest {

    static Bot bot;
    static Update update;
    static Message message;
    static Chat chat;
    static List<Command> handlers;
    static ApplicationContext context;

    @BeforeAll
    static void startBot() {
        update = mock(Update.class);
        message = mock(Message.class);
        chat = mock(Chat.class);
        context = mock(ApplicationContext.class);
        when(update.message()).thenReturn(message);

        var ctx = SpringApplication.run(BotApplication.class);
        ApplicationConfig applicationConfig = ctx.getBean(ApplicationConfig.class);
        bot = ctx.getBean(Bot.class);
        bot.start();

        var startCommandHandler = new StartCommand();
        var helpCommandHandler = new HelpCommand(mock(ApplicationContext.class));
        var listCommandHandler = new ListCommand();
        var trackCommandHandler = new TrackCommand();
        var untrackCommandHandler = new UntrackCommand();
        handlers = List.of(
            startCommandHandler,
            helpCommandHandler,
            trackCommandHandler,
            untrackCommandHandler,
            listCommandHandler
        );

    }


    @Test
    public void createMenuTest(){
        SetMyCommands actualRequest = bot.createMenu(handlers);

        SetMyCommands expectedRequest = new SetMyCommands(
            new BotCommand("/start", "Зарегистрировать пользователя"),
            new BotCommand("/help", "Вывести окно с командами"),
            new BotCommand("/track", "Начать отслеживание ссылки"),
            new BotCommand("/untrack", "Прекратить отслеживание ссылки"),
            new BotCommand("/list", "Вывести список отслеживаемых ссылок")
        );
        assertThat(actualRequest)
            .usingRecursiveComparison()
            .isEqualTo(expectedRequest);
    }

    @Test
    public void validHandleUpdateTest() {
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/start");
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(chat.username()).thenReturn("TestUser");

        var actualAnswer = bot.handleUpdate(update);

        var expectedAnswer = new SendMessage(
            1L,
            "Привет, *TestUser*\\!\n\n"
                + "Я бот для ___отслеживания обновлений_\r__ множества веб\\-ресурсов, которые тебе интересны\\! "
                + "Для получения списка доступных команд открой ___меню_\r__ или введи /help\\.\n\n"
                + "Ты ___успешно_\r__ зарегистрирован\\! "
                + "Можешь начинать отслеживать ссылки\\!"
        )
            .parseMode(ParseMode.MarkdownV2)
            .disableWebPagePreview(true);
        assertThat(actualAnswer)
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


            HelpCommand helpCommand = new HelpCommand(context);


            SendMessage response = helpCommand.handle(update);




//            ApplicationContext context = mock(ApplicationContext.class);
//            Method method = HelpCommand.class.getDeclaredMethod("generateAnswer");
//            method.setAccessible(true);
//            mockStatic(Command.class);
//
//            HelpCommand help = new HelpCommand(context);
//            String result = (String) method.invoke(help);

            var expectedAnswer = "*Список команд:*\n\n"
                + "*/start* \\-\\> _Зарегистрировать пользователя_\n"
                + "*/help* \\-\\> _Вывести окно с командами_\n"
                + "*/track* \\-\\> _Начать отслеживание ссылки_\n"
                + "*/untrack* \\-\\> _Прекратить отслеживание ссылки_\n"
                + "*/list* \\-\\> _Вывести список отслеживаемых ссылок_";

            AssertionsForClassTypes.assertThat(response)
                .isEqualTo(expectedAnswer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
