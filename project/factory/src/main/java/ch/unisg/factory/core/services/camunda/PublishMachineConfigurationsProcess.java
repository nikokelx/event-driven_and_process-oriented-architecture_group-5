package ch.unisg.factory.core.services.camunda;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("publish-machine-configurations")
public class PublishMachineConfigurationsProcess {

    @Autowired
    private ZeebeClient zeebeClient;

    @ZeebeWorker( type = "publish-machine-configurations", autoComplete = true)
    public void publishMachineConfigurations(final JobClient jobClient, final ActivatedJob activatedJob) {
        System.out.println("Event: Check Machine Status, Bla Bla Bla");

        HashMap variables = new HashMap();
        variables.put("machineSetup", "");
        variables.put("startProduction", "");

        zeebeClient.newPublishMessageCommand()
                .messageName("MachineConfigurations")
                .correlationKey("")
                .variables("{ \"machineSetup\" : \"\"}")
                .send();

        zeebeClient.newCompleteCommand(activatedJob.getKey()).variables("{ \"machineSetup\" : \"\"}").send();
    }

}
