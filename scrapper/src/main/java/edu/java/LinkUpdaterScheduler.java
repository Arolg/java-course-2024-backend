package edu.java;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;

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
