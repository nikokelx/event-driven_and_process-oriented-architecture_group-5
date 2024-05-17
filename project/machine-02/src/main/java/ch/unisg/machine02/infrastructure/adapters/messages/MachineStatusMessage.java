package ch.unisg.machine02.infrastructure.adapters.messages;

import ch.unisg.machine02.core.entities.Machine;
import ch.unisg.machine02.core.ports.out.MachineStatusEventPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Primary
@RequiredArgsConstructor
public class MachineStatusMessage  implements MachineStatusEventPort {

    @Value("${spring.kafka.topic}")
    private String topic;

    @Value("${spring.kafka.topic-custom-log}")
    private String topicCustomLog;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void toggleMachineStatus(Machine.MachineStatus machineStatus) {

        kafkaTemplate.send(topicCustomLog, "Send Event: Machine Status toggle.");

        kafkaTemplate.send(topic, machineStatus.getValue().toString());

    }
}
