package mirror.bathroom.config;

import mirror.bathroom.client.lifx.LifxLightBulbClient;
import mirror.bathroom.rest.BathroomController;
import mirror.bathroom.service.BathroomService;
import mirror.bathroom.service.lifx.LifxBathroomResponseConverter;
import mirror.telegram.service.TelegramBotService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@SpringBootConfiguration
@PropertySource("classpath:reflect-key.properties")
public class BathroomConfig {

    @Value("${lifx.apikey}")
    private String lifxApiKey;

    @Bean
    public BathroomController bathroomController(BathroomService bathroomService) {
        return new BathroomController(bathroomService);
    }

    @Bean
    public BathroomService bathroomService(LifxLightBulbClient client, LifxBathroomResponseConverter converter, TelegramBotService botService) {
        return new BathroomService(client, converter, botService);
    }

    @Bean
    public LifxLightBulbClient lifxLightBulbClient(RestTemplate restTemplate) {
        return new LifxLightBulbClient(restTemplate, lifxApiKey);
    }

    @Bean
    public LifxBathroomResponseConverter lifxBathroomResponseConverter() {
        return new LifxBathroomResponseConverter();
    }
}
