package ch.unisg.machines.core.services.camunda08;

import ch.unisg.machines.core.entities.Machine;
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

        // retrieve machine fill level
        double machineFillLevel = machine.getMachineFillLevel().getValue();

        // save machine fill level
        HashMap variables = new HashMap();
        variables.put("machineFillLevel", machineFillLevel);

        // send an event to the kafka topic machine fill level
        kafkaTemplate.send("machine-fill-level", String.valueOf(machineFillLevel));

        // complete camunda 8 service task
        zeebeClient.newCompleteCommand(activatedJob.getKey())
                .variables(variables)
                .send();



    }
}