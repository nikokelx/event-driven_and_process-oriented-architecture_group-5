package ch.unisg.factory.core.services;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("toggle-machines-process")
public class ToggleMachinesProcess {

    @Autowired
    private ZeebeClient client;

    @JobWorker(type = "toggle-machines-process")
    public void toggleMachines(final JobClient clientJob, final ActivatedJob job) {
        System.out.println("Event: Toggle machine process");

        client.newPublishMessageCommand()
                .messageName("ToggleMachine01")
                .correlationKey("machine01")
                .send();

        HashMap variables = new HashMap();
        variables.put("test", "Test");

        clientJob.newCompleteCommand(job.getKey()).variables(variables).send();
    }
}
