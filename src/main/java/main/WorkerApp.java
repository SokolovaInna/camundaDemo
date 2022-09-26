package main;
import org.camunda.bpm.client.ExternalTaskClient;
import java.util.Collections;

public class WorkerApp {

    public void run() {
        final ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8081/engine-rest")
                .build();
        client.subscribe("lesson6")
                .lockDuration(1000)
                .handler(((externalTask, externalTaskService) -> {
                    final String var1 = externalTask.getVariable("параметр1");
                    externalTaskService.complete(externalTask,
                            Collections.singletonMap("переменная-созданная-внешней-задачей",
                                    "Let's go, Brandon!"));
                })).open();
    }
    public static void main(final String[] args) {
        final WorkerApp app = new WorkerApp();
        app.run();
    }
}
