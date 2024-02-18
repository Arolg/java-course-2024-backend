package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.ChosenInlineResult;
import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.telegram.Bot;
import edu.java.bot.telegram.BotCommandList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.pengrad.telegrambot.model.Update;
import java.util.Map;


@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)


public class BotApplication {
    public static void main(String[] args) {

        var ctx = SpringApplication.run(BotApplication.class, args);
        ApplicationConfig applicationConfig = ctx.getBean(ApplicationConfig.class);
        System.out.println(applicationConfig);

        Bot bot = ctx.getBean(Bot.class);

        bot.start();


    }
}
