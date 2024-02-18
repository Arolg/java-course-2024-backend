package edu.java.bot.telegram;

import edu.java.bot.telegram.command.Command;
import edu.java.bot.telegram.command.HelpCommand;
import edu.java.bot.telegram.command.ListCommand;
import edu.java.bot.telegram.command.StartCommand;
import edu.java.bot.telegram.command.TrackCommand;
import edu.java.bot.telegram.command.UntrackCommand;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Getter
@Component
public class BotCommandList {

    private final List<Command> commands;

    @Autowired
    public BotCommandList(
        StartCommand start,
        HelpCommand help,
        TrackCommand track,
        UntrackCommand untrack,
        ListCommand list
    ) {
        this.commands = List.of(start, help, track, untrack, list);
    }

}
