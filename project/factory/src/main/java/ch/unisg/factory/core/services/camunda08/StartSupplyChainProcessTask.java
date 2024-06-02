package ch.unisg.factory.core.services.camunda08;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("start-supply-chain-process")
public class StartSupplyChainProcessTask {

    @Autowired
    private ZeebeClient zeebeClient;

    @JobWorker(type = "start-supply-chain-process", autoComplete = false)
    public void startSupplyChainProcess(ActivatedJob activatedJob) {

        zeebeClient.newPublishMessageCommand()
                .messageName("StartSupplyChainProcess")
                .correlationKey("start-supply-chain-process")
                .send();

        zeebeClient.newCompleteCommand(activatedJob.getKey())
                .send();
    }
}
