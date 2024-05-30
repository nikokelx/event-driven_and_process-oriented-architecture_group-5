package ch.unisg.factory.core.services.camunda08;

import ch.unisg.factory.core.entities.Outbox;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("publish-machine-fill-level-event")
public class PublishMachineFillLevelEvent {

    @Autowired
    private ZeebeClient zeebeClient;

    private Outbox outbox = Outbox.getOutbox();

    @JobWorker(type = "publish-machine-fill-level", autoComplete = false)
    public void publishMachineFillLevel(final JobClient jobClient, final ActivatedJob activatedJob) {

        System.out.println("Publish last outbox element: " + outbox.getLastMachineFillLevel());

        zeebeClient.newCompleteCommand(activatedJob.getKey())
                .send();
    }
}
