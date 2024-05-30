package ch.unisg.machines.core.services.camunda08;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("publish-machine-setup")
public class PublishMachineSetupEvent {

    @Autowired
    private ZeebeClient zeebeClient;

    @JobWorker(type = "publish-machine-setup-task", autoComplete = false)
    public void publishMachineSetup(final JobClient jobClient, final ActivatedJob activatedJob) {


        System.out.println("Event: Publish Machine Setup");


        // publish a message

        zeebeClient.newPublishMessageCommand()
                .messageName("MachineSetup")
                .correlationKey("machine01")
                .send();


        // complete camunda 8 service task

        zeebeClient.newCompleteCommand(activatedJob.getKey())
                .send();

    }
}
