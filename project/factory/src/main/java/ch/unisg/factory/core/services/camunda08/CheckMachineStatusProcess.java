package ch.unisg.factory.core.services.camunda08;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("check-machine-status")
public class CheckMachineStatusProcess {

    @Autowired
    private ZeebeClient client;

    @JobWorker(type = "check-machine-status")
    public void checkMachineStatus(final JobClient clientJob, final ActivatedJob job) {
        System.out.println("Event: Check Machine Status");

        client.newPublishMessageCommand()
                .messageName("MachineStatus")
                .correlationKey("machine01")
                .send();

        HashMap variables = new HashMap();

        clientJob.newCompleteCommand(job.getKey()).variables(variables).send();

    }
}