package mirror.modules.config;

import mirror.bathroom.module.BathroomModule;
import mirror.bathroom.service.BathroomService;
import mirror.modules.ModulesRunner;
import mirror.tasks.TasksModule;
import mirror.tasks.TasksService;
import mirror.weather.module.WeatherModule;
import mirror.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootConfiguration
@PropertySource("classpath:reflect.properties")
public class ModuleConfig {

    @Value("${modules.executor.numberOfThreads}")
    public int executorThreads;

    @Value("${modules.weather.websocket.endpoint}")
    private String weatherEndpoint;

    @Value("${modules.bathroom.websocket.endpoint}")
    private String bathroomEndpoint;

    @Value("${modules.bathroom.executor.intervalSeconds}")
    private int bathroomIntervalSeconds;

    @Value("${modules.weather.executor.intervalSeconds}")
    private int weatherIntervalSeconds;

    @Value("${modules.tasks.executor.intervalSeconds}")
    private int tasksIntervalSeconds;

    @Value("${modules.tasks.websocket.endpoint}")
    private String tasksEndpoint;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Bean
    public ModulesRunner modulesRunner(ScheduledExecutorService executorService,
                                       SimpMessagingTemplate simpMessagingTemplate,
                                       WeatherModule weatherModule,
                                       BathroomModule bathroomModule) {
        return new ModulesRunner(executorService, simpMessagingTemplate,
                weatherModule, bathroomModule);
    }

    @Bean
    public WeatherModule weatherModule(WeatherService weatherService) {
        return new WeatherModule(weatherService, weatherEndpoint, TimeUnit.SECONDS.toMillis(weatherIntervalSeconds));
    }

    @Bean
    public TasksModule tasksModule(TasksService tasksService) {
        return new TasksModule(tasksService, tasksEndpoint, TimeUnit.SECONDS.toMillis(tasksIntervalSeconds));
    }

    @Bean
    public BathroomModule bathroomModule(BathroomService bathroomService) {
        return new BathroomModule(bathroomService, bathroomEndpoint, TimeUnit.SECONDS.toMillis(bathroomIntervalSeconds));
    }

    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(executorThreads);
    }
}
