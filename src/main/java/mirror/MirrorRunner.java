package mirror;

import mirror.modules.ModulesRunner;
import mirror.modules.config.ModuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;

import java.util.Arrays;

@SpringBootApplication
public class MirrorRunner {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(MirrorRunner.class, args);

        ModulesRunner modulesRunner = applicationContext.getBean(ModulesRunner.class);
        modulesRunner.initModules();
    }
}
