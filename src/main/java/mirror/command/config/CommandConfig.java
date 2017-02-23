package mirror.command.config;

import mirror.command.StringCommander;
import mirror.modules.generic.AbstractMirrorModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

@Configuration
public class CommandConfig {
    @Bean
    public StringCommander stringCommander(List<AbstractMirrorModule> moduleList, SimpMessagingTemplate simpMessagingTemplate) {
        return new StringCommander(moduleList, simpMessagingTemplate);
    }
}
