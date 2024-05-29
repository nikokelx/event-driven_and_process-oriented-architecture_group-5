package ch.unisg.machine01.core.services.camunda08;

import ch.unisg.machine01.core.entities.Machine;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("publish-machine-fill-level")
public class PublishMachineFillLevelEvent {

    @Autowired
    private ZeebeClient zeebeClient;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private Machine machine = Machine.getMachine();

    @JobWorker(type = "publish-machine-fill-level", autoComplete = false)
    public void publishMachineFillLevel(ActivatedJob activatedJob, JobClient client) {

        System.out.println("Event: Publish Machine Fill Level");

        double machineFillLevel = machine.getMachineFillLevel().getValue();

        HashMap variables = new HashMap();
        variables.put("machineFillLevel", machineFillLevel);

        kafkaTemplate.send("machine-fill-level", String.valueOf(machineFillLevel));

        zeebeClient.newCompleteCommand(activatedJob.getKey())
                .variables(variables)
                .send();
    }
}