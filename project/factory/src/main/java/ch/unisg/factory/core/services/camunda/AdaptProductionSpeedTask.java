package ch.unisg.factory.core.services.camunda;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adapt-production-speed")
public class AdaptProductionSpeedTask {

    @Autowired
    private ZeebeClient zeebeClient;

    @JobWorker(type = "adapt-production-speed-task", autoComplete = false)
    public void adaptProductionSpeed(final JobClient jobClient, final ActivatedJob activatedJob) {

        zeebeClient.newCompleteCommand(activatedJob.getKey())
                .send();
    }
}
