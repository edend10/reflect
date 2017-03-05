package mirror.tasks;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.TasksScopes;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.TaskLists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class GoogleTasksClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleTasksClient.class);

    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/google-tasks");
    private static final String APPLICATION_NAME = "reflect";
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();
    private static HttpTransport HTTP_TRANSPORT;

    //delete credentials if change scope
    private static final List<String> SCOPES =
            Arrays.asList(TasksScopes.TASKS_READONLY);

    private Tasks service;

    public GoogleTasksClient() {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
            service = buildGoogleTasksService();
        } catch (Exception e) {
            LOGGER.error("Google tasks client failed to initiate", e);
        }
    }

    private Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                GoogleTasksClient.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");

        return credential;
    }

    private Tasks buildGoogleTasksService() throws IOException {
        Credential credential = authorize();
        return new Tasks.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<TaskList> getTaskLists() throws IOException {
        TaskLists result = service.tasklists().list()
                .setMaxResults(10L)
                .execute();
        return result.getItems();
    }

    public List<Task> getTasks(String listId) throws IOException {
        com.google.api.services.tasks.model.Tasks result = service.tasks().list(listId)
                .setMaxResults(10L)
                .execute();
        return result.getItems();
    }
}
