package edu.java.bot;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.telegram.Bot;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)


public class BotApplication {
    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }

    @Bean
    public ApplicationRunner initializeBot(Bot bot) {
        return args -> {
            bot.start();
        };
    }
}
