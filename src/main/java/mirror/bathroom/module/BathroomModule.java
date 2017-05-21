package mirror.bathroom.module;

import mirror.bathroom.dao.BathroomSittingDto;
import mirror.bathroom.model.response.BathroomResponse;
import mirror.bathroom.service.BathroomService;
import mirror.modules.generic.AbstractMirrorModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class BathroomModule extends AbstractMirrorModule {
    private final static String GET_BATHROOM_ENDPOINT = "get";

    private final static Logger LOGGER = LoggerFactory.getLogger(BathroomModule.class);
    private final BathroomService service;
    //TODO: make enum
    private String retainedBathroomState;
    private BathroomSittingDto currentSitting;

    public BathroomModule(BathroomService service, String endpoint, long intervalMillis) {
        super(endpoint, intervalMillis);
        this.service = service;
        retainedBathroomState = "NA";
        currentSitting = new BathroomSittingDto();
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
                        service.alertIfRegistered(currentState);
                        if (currentState.equals("occupied")) {
                            currentSitting.setStartTime(LocalDateTime.now());
                        } else {
                            currentSitting.setEndTime(LocalDateTime.now());
                            if (currentSitting.isComplete()) {
                                service.saveSitting(currentSitting);
                                currentSitting = new BathroomSittingDto();
                            }
                        }
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
