package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.util.List;

@Component

public class GetInforamtionCommand implements BaseCommand {

    @Override
    public String command() {
        return "info";
    }

    @Override
    public SendMessage handle(Long chatId, String file) {
        return new SendMessage(chatId, file);
    }
}
