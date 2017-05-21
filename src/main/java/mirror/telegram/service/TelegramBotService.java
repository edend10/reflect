package mirror.telegram.service;

import mirror.telegram.bot.TelegramBathroomBuddyBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TelegramBotService {
    private final static Logger LOGGER = LoggerFactory.getLogger(TelegramBotService.class);

    private final TelegramBathroomBuddyBot bathroomBot;
    private final TelegramBotsApi botsApi;

    public TelegramBotService(String bathroomBotApiKey, String bathroomBotUsername) {
        ApiContextInitializer.init();
        this.bathroomBot = new TelegramBathroomBuddyBot(bathroomBotApiKey, bathroomBotUsername);
        List<TelegramLongPollingBot> bots = Arrays.asList(bathroomBot);
        botsApi = new TelegramBotsApi();
//        registerBots(bots);
    }

    private void registerBots(List<TelegramLongPollingBot> bots) {
        bots.forEach(bot -> {
                try {
                    botsApi.registerBot(bot);
                } catch (TelegramApiException e) {
                    LOGGER.error("Failed to register bot", e);
                }
        });
    }

    public TelegramBathroomBuddyBot getBathroomBot() {
        return bathroomBot;
    }

    public void broadcastMessageBathroomBot(String message) {
        bathroomBot.broadcastMessage(message);
    }
}
