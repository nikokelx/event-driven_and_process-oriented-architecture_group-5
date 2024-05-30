package ch.unisg.factory.core.services.camunda08;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("receive-machine-setup-event")
public class ReceiveMachineSetupEvent {

    @Autowired
    private ZeebeClient zeebeClient;

    @JobWorker(type = "receive-machine-setup", autoComplete = false)
    public void receiveMachineSetup(JobClient jobClient, ActivatedJob activatedJob) {



        zeebeClient.newCompleteCommand(activatedJob.getKey()).send();
    }
}

