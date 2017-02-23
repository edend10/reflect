package mirror.weather.module;

import mirror.modules.generic.AbstractMirrorModule;
import mirror.weather.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class WeatherModule extends AbstractMirrorModule {

    private final static String GET_WEATHER_ENDPOINT = "get";

    private final static Logger LOGGER = LoggerFactory.getLogger(WeatherModule.class);
    private final WeatherService weatherService;

    public WeatherModule(WeatherService weatherService, String endpoint, long intervalMillis) {
        super(endpoint, intervalMillis);
        this.weatherService = weatherService;
    }

    @Override
    public ScheduledFuture<?> scheduleTask(ScheduledExecutorService executorService, SimpMessagingTemplate webSocketMessenger) {
        final Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    LOGGER.debug("Fetching and pushing weather response from weather service...");
                    sendMessage(webSocketMessenger, getEndpoint(GET_WEATHER_ENDPOINT), weatherService::getWeather);
                } catch (Exception e) {
                    LOGGER.error("Weather module failed:", e);
                }
            }
        };

        return executorService.scheduleAtFixedRate(
                task, 5000, getIntervalMillis(), TimeUnit.MILLISECONDS);
    }
}
