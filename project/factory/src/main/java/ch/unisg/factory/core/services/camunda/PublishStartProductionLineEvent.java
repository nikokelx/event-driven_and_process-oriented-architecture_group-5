package ch.unisg.factory.core.services.camunda;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("publish-start-production-line")
public class PublishStartProductionLineEvent {

    @Autowired
    private ZeebeClient zeebeClient;

    @JobWorker(type = "publish-start-production", autoComplete = false)
    public void publishStartProductionLineEvent(final JobClient jobClient, final ActivatedJob activatedJob) {

        zeebeClient.newPublishMessageCommand()
                .messageName("StartProduction")
                .correlationKey("factory")
                .send();

        zeebeClient.newCompleteCommand(activatedJob.getKey())
                .send();
    }

}
