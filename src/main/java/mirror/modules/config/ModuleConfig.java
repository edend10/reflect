package mirror.modules.config;

import mirror.modules.ModulesRunner;
import mirror.weather.module.WeatherModule;
import mirror.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
@PropertySource("classpath:reflect.properties")
public class ModuleConfig {

    @Value("${modules.executor.numberOfThreads}")
    public int executorThreads;

    @Value("${modules.weather.websocket.endpoint}")
    private String weatherEndpoint;

    @Value("${modules.weather.executor.intervalMinutes}")
    private int weatherIntervalMinutes;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Bean
    public ModulesRunner modulesRunner(ScheduledExecutorService executorService,
                                       SimpMessagingTemplate simpMessagingTemplate,
                                       WeatherModule weatherModule) {
        return new ModulesRunner(executorService, simpMessagingTemplate, weatherModule);
    }

    @Bean
    public WeatherModule weatherModule(WeatherService weatherService) {
        return new WeatherModule(weatherService, weatherEndpoint, TimeUnit.MINUTES.toMillis(weatherIntervalMinutes));
    }

    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(executorThreads);
    }
}
