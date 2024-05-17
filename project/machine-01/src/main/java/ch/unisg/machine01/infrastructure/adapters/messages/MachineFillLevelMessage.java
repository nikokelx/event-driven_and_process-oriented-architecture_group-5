package ch.unisg.machine01.infrastructure.adapters.messages;

import ch.unisg.machine01.core.entities.Machine;
import ch.unisg.machine01.core.entities.MachineData;
import ch.unisg.machine01.core.ports.out.FillLevelEventPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Primary
@RequiredArgsConstructor
public class MachineFillLevelMessage implements FillLevelEventPort {

    @Value("${spring.kafka.topic-fill-level}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, MachineData> kafkaMachineDataTemplate;

    @Override
    public void publishFillLevel(Machine.MachineFillLevel machineFillLevel) {
        kafkaTemplate.send(topic, String.valueOf(machineFillLevel.getValue()));
    }

    @Override
    public void publishMachineData(MachineData machineData) {
        kafkaMachineDataTemplate.send(topic, machineData);
    }
}
