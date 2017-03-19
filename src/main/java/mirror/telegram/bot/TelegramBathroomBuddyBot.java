package mirror.telegram.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.SubstituteLogger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TelegramBathroomBuddyBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBathroomBuddyBot.class);

    private static final String CHAT_IDS_FILE = "telegram-chat-ids.reflect";

    private final String apiKey;
    private final String botUsername;
    private Set<Long> chatIds;

    public TelegramBathroomBuddyBot(String apiKey, String botUsername) {
        this.apiKey = apiKey;
        this.botUsername = botUsername;
        this.chatIds = readChatIdsFromFile();
    }

    @Override
    public void onUpdateReceived(Update update) {
        LOGGER.info("Received upadte from bot", update);
        Message msg = update.getMessage();
        if (msg.getText().equals("/start")) {
            if (!chatIds.contains(msg.getChatId())) {
                chatIds.add(msg.getChatId());
                writeChatIdsToFile();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onClosing() {
    }

    @Override
    public String getBotToken() {
        return apiKey;
    }

    public void broadcastMessage(String content) {
        chatIds.forEach(chatId -> {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(content);
            try {
                this.sendMessage(message);
            } catch (TelegramApiException e) {
                LOGGER.error("Failed to message chatId=" + chatId, e);
            }
        });
    }

    private Set<Long> readChatIdsFromFile() {
        LOGGER.info("Trying to fetch chat ids from file...");
        try (FileInputStream fis = new FileInputStream(CHAT_IDS_FILE)) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (Set<Long>) ois.readObject();
        } catch (Exception e) {
            LOGGER.warn("Failed reading telegram chat ids file", e);
            return new HashSet<>();
        }
    }

    private void writeChatIdsToFile() {
        LOGGER.info("Trying to write chat ids to file...");
        try (FileOutputStream fos = new FileOutputStream(CHAT_IDS_FILE)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(chatIds);
        } catch (Exception e) {
            LOGGER.warn("Failed writing telegram chat ids file", e);
        }
    }
}
