package ch.unisg.machine01.core.services.camunda;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("publish-machine-setup")
public class PublishMachineSetupEvent {

    @Autowired
    private ZeebeClient zeebeClient;

    @JobWorker(type = "publish-machine-setup-task", autoComplete = false)
    public void publishMachineSetup(final JobClient jobClient, final ActivatedJob activatedJob) {

        System.out.println("Event: Publish Machine Setup");

        HashMap variables = new HashMap();
        variables.put("Test", "Test");

        zeebeClient.newPublishMessageCommand()
                .messageName("MachineSetup")
                .correlationKey("Active")
                .variables(variables)
                .send();

        zeebeClient.newCompleteCommand(activatedJob.getKey())
                .send();
    }
}
