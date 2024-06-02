package ch.unisg.logistics.services.camunda;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("transport")
public class TransportTask {

    @Autowired
    private ZeebeClient zeebeClient;

    @JobWorker(type = "transport-task", autoComplete = false)
    public void transport(ActivatedJob activatedJob, @Variable int acc) throws InterruptedException {

        switch (acc) {
            case 0 -> Thread.sleep(15000);
            case 1 -> Thread.sleep(12000);
            case 2 -> Thread.sleep(10000);
            default -> {
                System.out.println("------ Using default transport speed (MEDIUM) ------");
                Thread.sleep(12000);
            }
        }

        zeebeClient.newCompleteCommand(activatedJob.getKey()).send();
    }
}
