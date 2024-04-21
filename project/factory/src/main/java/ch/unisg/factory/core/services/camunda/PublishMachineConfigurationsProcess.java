package ch.unisg.factory.core.services.camunda;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("publish-machine-configurations")
public class PublishMachineConfigurationsProcess {

    @Autowired
    ZeebeClient zeebeClient;

    @JobWorker(type = "publish-machine-configurations")
    public void publishMachineConfigurations(final JobClient jobClient, final ActivatedJob activatedJob) {
        System.out.println("Event: Check Machine Status");

        zeebeClient.newPublishMessageCommand()
                .messageName("Event_135q3c0")
                .correlationKey("machines")
                .send();

        HashMap variables = new HashMap();
        variables.put("machineSetup", "");

        zeebeClient.newCompleteCommand(activatedJob.getKey()).variables(variables).send();
    }

}
