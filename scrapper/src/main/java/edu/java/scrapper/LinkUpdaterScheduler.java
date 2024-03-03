package edu.java.scrapper;

import java.util.logging.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@ConditionalOnProperty(value = "app.scheduler.enable", havingValue = "true")
public class LinkUpdaterScheduler {
    Logger logger = Logger.getLogger("LinkUpdaterScheduler");

    @Scheduled(fixedDelayString = "#{@scheduler.interval}")
    public void update() {
        logger.info("Executing link update task");
    }
}
