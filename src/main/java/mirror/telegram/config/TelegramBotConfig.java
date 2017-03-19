package mirror.telegram.config;

import mirror.telegram.bot.TelegramBathroomBuddyBot;
import mirror.telegram.service.TelegramBotService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;
import java.util.List;

@SpringBootConfiguration
@PropertySource("classpath:reflect.properties")
@PropertySource("classpath:reflect-key.properties")
public class TelegramBotConfig {

    @Value("${telegram.bathroombot.apikey}")
    private String telegramBathroomBotApiKey;

    @Value("${telegram.bathroombot.username}")
    private String telegramBathroomBotUsername;

    @Bean
    public TelegramBotService telegramBotService() {
        return new TelegramBotService(telegramBathroomBotApiKey, telegramBathroomBotUsername);
    }
}
