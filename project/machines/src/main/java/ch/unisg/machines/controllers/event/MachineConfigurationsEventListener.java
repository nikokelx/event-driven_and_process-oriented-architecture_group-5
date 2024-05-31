package ch.unisg.machines.controllers.event;

import ch.unisg.machines.core.entities.Machine;
import io.camunda.zeebe.client.ZeebeClient;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@RequiredArgsConstructor
@Component
public class MachineConfigurationsEventListener {

    @Autowired
    private ZeebeClient zeebeClient;

    private Machine machine = Machine.getMachine();

    @KafkaListener(topics = "machine-configurations", containerFactory = "kafkaListenerStringFactory")
    public void listen(String message) {


        // Set machine production speed
        machine.setMachineProductionSpeed(new Machine.MachineProductionSpeed(Integer.valueOf(message)));

        // Save machine production speed
        HashMap variables = new HashMap();
        variables.put("production_speed", message);

        // Create a new process (Camunda 8)
        zeebeClient.newCreateInstanceCommand().bpmnProcessId("Process_0r1fn32").latestVersion().variables(variables).send();


    }
}
