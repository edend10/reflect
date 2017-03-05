package mirror.bathroom.module;

import mirror.bathroom.model.response.BathroomResponse;
import mirror.bathroom.service.BathroomService;
import mirror.modules.generic.AbstractMirrorModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class BathroomModule extends AbstractMirrorModule {
    private final static String GET_BATHROOM_ENDPOINT = "get";

    private final static Logger LOGGER = LoggerFactory.getLogger(BathroomModule.class);
    private final BathroomService service;
    private String retainedBathroomState;

    public BathroomModule(BathroomService service, String endpoint, long intervalMillis) {
        super(endpoint, intervalMillis);
        this.service = service;
        retainedBathroomState = "NA";
    }

    @Override
    public ScheduledFuture<?> scheduleTask(ScheduledExecutorService executorService, SimpMessagingTemplate webSocketMessenger) {
        final Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    LOGGER.debug("Fetching and pushing bathroom response from bathroom service...");
                    BathroomResponse response = service.getBathroomState();
                    String currentState = response.getState();
                    if (!currentState.equals(retainedBathroomState)) {
                        sendMessage(webSocketMessenger, getEndpoint(GET_BATHROOM_ENDPOINT), response);
                        retainedBathroomState = currentState;
                    }
                } catch (Exception e) {
                    retainedBathroomState = "NA";
                    LOGGER.error("Bathroom module failed:", e);
                }
            }
        };

        return executorService.scheduleAtFixedRate(
                task, 5000, getIntervalMillis(), TimeUnit.MILLISECONDS);
    }
}
