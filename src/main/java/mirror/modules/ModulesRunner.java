package mirror.modules;

import mirror.modules.generic.AbstractMirrorModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class ModulesRunner {

    private final ScheduledExecutorService executorService;
    private final SimpMessagingTemplate webSocketMessenger;
    private final List<AbstractMirrorModule> modules;

    public ModulesRunner(ScheduledExecutorService executorService,
                         SimpMessagingTemplate webSocketMessenger,
                         AbstractMirrorModule ...modulesArr) {
        this.executorService = executorService;
        this.webSocketMessenger = webSocketMessenger;
        this.modules = Arrays.asList(modulesArr);
    }

    public void initModules() {
        modules.forEach(module ->
                        module.scheduleTask(executorService, webSocketMessenger)
        );

    }
}
