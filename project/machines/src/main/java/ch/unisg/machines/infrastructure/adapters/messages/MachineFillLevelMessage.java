package ch.unisg.machines.infrastructure.adapters.messages;

import ch.unisg.machines.core.entities.Machine;
import ch.unisg.machines.core.ports.out.FillLevelEventPort;
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

    @Value("${spring.kafka.topic-machine-fill-level}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publishFillLevel(Machine.MachineFillLevel machineFillLevel) {

        System.out.println("Fill level " + String.valueOf(machineFillLevel.getValue()));
        kafkaTemplate.send(topic, String.valueOf(machineFillLevel.getValue()));
    }
}
