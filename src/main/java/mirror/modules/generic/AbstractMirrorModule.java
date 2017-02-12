package mirror.modules.generic;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public abstract class AbstractMirrorModule {

    private final String endpoint;
    private final long intervalMillis;

    public AbstractMirrorModule(String endpoint, long intervalMillis) {
        this.endpoint = endpoint;
        this.intervalMillis = intervalMillis;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public long  getIntervalMillis() {
        return intervalMillis;
    }

    public abstract ScheduledFuture<?> scheduleTask(ScheduledExecutorService executorService, SimpMessagingTemplate webSocketMessenger);
}
