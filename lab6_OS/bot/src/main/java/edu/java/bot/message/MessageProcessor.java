package edu.java.bot.message;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.BaseCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class MessageProcessor {
    private final List<BaseCommand> commands;

    @Autowired
    public MessageProcessor(
        List<BaseCommand> commands
    ) {
        this.commands = commands;
    }

    public SendMessage process(String com, Long chatId, String file) {
        for (var command : commands) {
            if (command.supports(com)) {
                return command.handle(chatId, file);
            }
        }
        return new SendMessage(chatId, "");
    }
}
