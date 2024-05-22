package ch.unisg.machine01.infrastructure.adapters.messages;

import ch.unisg.machine01.core.entities.Machine;
import ch.unisg.machine01.core.ports.out.MachineStatusEventPort;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Primary
@RequiredArgsConstructor
public class MachineStatusMessage  implements MachineStatusEventPort {

    // retrieve the topic for the machine status
    @Value("${spring.kafka.topic-machine-status}")
    private String topic;

    // retrieve the topic for creating custom logs
    @Value("${spring.kafka.topic-custom-log}")
    private String topicCustomLog;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final Machine machine = Machine.getMachine();

    @Override
    public void toggleMachineStatus(Machine.MachineStatus machineStatus) {
        System.out.println("TEST");
        System.out.println(machine.getMachineId().getValue());
        System.out.println("ENDE");
        // emit a custom log event for debugging
        kafkaTemplate.send(topicCustomLog, "Send Event: Machine Status toggle.");

        String machineId = String.valueOf(machine.getMachineId().getValue());

        // emit a machine event for publishing the machine status
        kafkaTemplate.send(topic, machineId, machineStatus.getValue().toString());
    }
}
