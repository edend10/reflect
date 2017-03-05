package mirror.tasks;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootConfiguration
public class TasksConfig {

    @Bean
    public TasksService tasksService(GoogleTasksClient tasksClient) {
        return new TasksService(tasksClient);
    }

    @Bean
    public GoogleTasksClient googleTasksClient() {
        return new GoogleTasksClient();
    }
}
