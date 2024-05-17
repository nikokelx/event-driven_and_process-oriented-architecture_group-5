package ch.unisg.machine02.infrastructure.adapters.messages;

import ch.unisg.machine02.core.entities.Machine;
import ch.unisg.machine02.core.ports.out.FillLevelEventPort;
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

    @Override
    public void publishFillLevel(Machine.MachineFillLevel machineFillLevel) {

        kafkaTemplate.send(topic, String.valueOf(machineFillLevel.getValue()));

    }
}
