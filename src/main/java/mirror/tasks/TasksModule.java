package mirror.tasks;

import mirror.modules.generic.AbstractMirrorModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TasksModule extends AbstractMirrorModule {

    private final static Logger LOGGER = LoggerFactory.getLogger(TasksModule.class);
    private final TasksService tasksService;

    public TasksModule(TasksService tasksService, String endpoint, long intervalMillis) {
        super(endpoint, intervalMillis);
        this.tasksService = tasksService;
    }

    @Override
    public ScheduledFuture<?> scheduleTask(ScheduledExecutorService executorService, SimpMessagingTemplate webSocketMessenger) {
        final Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    LOGGER.debug("Fetching and pushing tasks from tasks service...");
                    //sendMessage(webSocketMessenger, getEndpoint(GET_WEATHER_ENDPOINT), tasksService::getTasks);
                } catch (Exception e) {
                    LOGGER.error("Tasks module failed", e);
                }
            }
        };

        return executorService.scheduleAtFixedRate(
                task, 5000, getIntervalMillis(), TimeUnit.MILLISECONDS);
    }
}
