package mirror.tasks;

import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.TaskLists;
import mirror.service.AbstractMirrorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class TasksService extends AbstractMirrorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TasksService.class);
    private final GoogleTasksClient tasksClient;

    public TasksService(GoogleTasksClient tasksClient) {
        this.tasksClient = tasksClient;
    }

    public void getTasks() {
        try {
            List<TaskList> taskLists = tasksClient.getTaskLists();
            taskLists.forEach(taskList -> {
                try {
                    tasksClient.getTasks(taskList.getId()).forEach(task -> {
                        System.out.println(task.getTitle() + " - " + task.getStatus());
                    });
                } catch(Exception e) {
                    LOGGER.error("Tasks service failed fetching individual tasks");
                }
            });
        } catch (Exception e) {
            LOGGER.error("Tasks service failed fetching task lists", e);
        }
    }
}
