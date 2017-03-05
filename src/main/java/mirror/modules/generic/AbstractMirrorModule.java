package mirror.modules.generic;

import com.google.common.collect.ImmutableMap;
import mirror.modules.model.ModuleVisibility;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AbstractMirrorModule {

    private static final String TOPIC_ENDPOINT = "topic";
    private static final String HIDDEN_ENDPOINT = "hidden";

    private final String baseEndpoint;
    private final long intervalMillis;
    private final Map<String, Consumer<SimpMessagingTemplate>> commandMap;

    private ModuleVisibility visibility;

    public AbstractMirrorModule(String endpoint, long intervalMillis) {
        this.baseEndpoint = endpoint;
        this.intervalMillis = intervalMillis;
        this.visibility = new ModuleVisibility();
        this.commandMap = buildCommandMap();
    }

    public ModuleVisibility getVisibility() {
        return visibility;
    }

    public String getBaseEndpoint() {
        return baseEndpoint;
    }

    public long  getIntervalMillis() {
        return intervalMillis;
    }

    public abstract ScheduledFuture<?> scheduleTask(ScheduledExecutorService executorService, SimpMessagingTemplate webSocketMessenger);

    public void showModule(SimpMessagingTemplate webSocketMessenger) {
        visibility.setHidden(false);
        sendMessage(webSocketMessenger, getEndpoint(HIDDEN_ENDPOINT), this::getVisibility);
    }

    public void hideModule(SimpMessagingTemplate webSocketMessenger) {
        visibility.setHidden(true);
        sendMessage(webSocketMessenger, getEndpoint(HIDDEN_ENDPOINT), this::getVisibility);
    }

    public void sendMessage(SimpMessagingTemplate webSocketMessenger, String endpoint, Supplier<Object> supplier) {
        webSocketMessenger.convertAndSend(endpoint, supplier.get());
    }

    public void sendMessage(SimpMessagingTemplate webSocketMessenger, String endpoint, Object payload) {
        webSocketMessenger.convertAndSend(endpoint, payload);
    }

    public String getEndpoint(String... endpoints) {
        return String.format("/%s/%s/%s", TOPIC_ENDPOINT, getBaseEndpoint(),
                Arrays.asList(endpoints).stream().collect(Collectors.joining("/")));
    }

    public boolean execute(String command, SimpMessagingTemplate webSocketMessenger) {
        if (commandMap.containsKey(command)) {
            commandMap.get(command).accept(webSocketMessenger);
            return true;
        } else {
            return false;
        }
    }

    private Map<String, Consumer<SimpMessagingTemplate>> buildCommandMap() {
        ImmutableMap.Builder<String, Consumer<SimpMessagingTemplate>> builder = new ImmutableMap.Builder();
        builder.put("show", this::showModule);
        builder.put("hide", this::hideModule);
        return builder.build();
    }
}
