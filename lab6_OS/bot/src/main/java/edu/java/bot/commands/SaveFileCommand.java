package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class SaveFileCommand implements BaseCommand {

    @Override
    public String command() {
        return "save";
    }

    @Override
    public SendMessage handle(Long chatId, String file) {
        return new SendMessage(chatId, file);
    }
}
