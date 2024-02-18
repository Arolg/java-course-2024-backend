package edu.java.bot;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.telegram.Bot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)


public class BotApplication {
    public static void main(String[] args) {

        var ctx = SpringApplication.run(BotApplication.class, args);
        ApplicationConfig applicationConfig = ctx.getBean(ApplicationConfig.class);

        Bot bot = ctx.getBean(Bot.class);

        bot.start();


    }
}
