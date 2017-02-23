package mirror;

import mirror.command.StringCommander;
import mirror.modules.ModulesRunner;
import mirror.modules.config.ModuleConfig;
import mirror.speech.SpeechRecognizer;
import mirror.speech.SphinxSpeechRecognizerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.Scanner;

@SpringBootApplication
public class MirrorRunner {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(MirrorRunner.class, args);

        ModulesRunner modulesRunner = applicationContext.getBean(ModulesRunner.class);
        modulesRunner.initModules();

        StringCommander stringCommander = applicationContext.getBean(StringCommander.class);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type a command...\n");
        String command = scanner.nextLine();
        while(!command.equals("stop")) {
            stringCommander.executeCommand(command);
            System.out.println("Type another command...\n");
            command = scanner.nextLine();
        }
    }
}
