package edu.java.bot;

import cn.tdchain.cb.util.TdcbConfig;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.message.MessageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Bot extends TelegramLongPollingBot {
    private final TelegramBot bot;
    private final MessageProcessor processor;
    private final Long chatId = 123L;
    private final Map<Long, String> files = new HashMap<>();

    @Autowired
    public Bot(
        @Value("${app.telegram-token}") String token,
        MessageProcessor userMessageProcessor
    ) {
        this.bot = new TelegramBot(token);
        this.processor = userMessageProcessor;
    }

    public void addFile(Long chatId, String file) throws TelegramApiException {
        SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(file);
        var res = execute(message);
        files.put(Long.valueOf(res.getMessageId()), file);
    }

    public String getFile(Long chatId, String name) {
        return files.get(chatId);
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return null;
    }
}
