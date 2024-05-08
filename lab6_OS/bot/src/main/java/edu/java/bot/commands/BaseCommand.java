package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface BaseCommand {

    String command();
    SendMessage handle(Long chatId, String file);

    default boolean supports(String command) {
        return command.startsWith(command());
    }
}
