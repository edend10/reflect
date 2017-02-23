package mirror.command;

import mirror.modules.generic.AbstractMirrorModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;
import java.util.Optional;

public class StringCommander {

    private final static Logger LOGGER = LoggerFactory.getLogger(StringCommander.class);

    private final List<AbstractMirrorModule> modules;
    private final SimpMessagingTemplate webSocketMessenger;

    public StringCommander(List<AbstractMirrorModule> modules, SimpMessagingTemplate webSocketMessenger) {
        this.modules = modules;
        this.webSocketMessenger = webSocketMessenger;
    }

    public void executeCommand(String commandLine) {
        String[] cmdArr = commandLine.split(" ");
        if (cmdArr.length != 2) {
            LOGGER.warn("Invalid command");
            return;
        }
        String command = cmdArr[0];
        String moduleName = cmdArr[1];
        if ("all".equals(moduleName)) {
            modules.forEach(module -> {
                if (!module.execute(command, webSocketMessenger)) {
                    LOGGER.warn("Module {} unaware of command {}", module.getBaseEndpoint(), command);
                }
            });
        } else {
            Optional<AbstractMirrorModule> selectedModuleOptional = modules
                    .stream()
                    .filter(module -> module.getBaseEndpoint().equals(moduleName))
                    .findAny();
            selectedModuleOptional.ifPresent(module -> {
                if (!module.execute(command, webSocketMessenger)) {
                    LOGGER.warn("Module {} unaware of command {}", module.getBaseEndpoint(), command);
                }
            });
        }
    }
}