package ch.unisg.factory.core.services.camunda08;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.Variable;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service("publish-machine-configurations")
public class PublishMachineConfigurationsProcess {

    @Autowired
    private ZeebeClient zeebeClient;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @ZeebeWorker( type = "publish-machine-configurations", autoComplete = false)
    public void publishMachineConfigurations(
            final JobClient jobClient,
            final ActivatedJob activatedJob,
            @Variable int productionSpeed

    ) {

        System.out.println("Event: Publish Machine Configurations");

        kafkaTemplate.send("machine-configurations", "machine-01", String.valueOf(productionSpeed));

        zeebeClient.newCompleteCommand(activatedJob.getKey())
                .send();
    }

}
