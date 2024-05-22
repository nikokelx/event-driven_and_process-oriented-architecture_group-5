package ch.unisg.machine01.infrastructure.adapters.messages.stream;

import ch.unisg.machine01.core.ports.out.streams.MachineDataStreamPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Primary
@Component
@RequiredArgsConstructor
public class MachineStreamMessage implements MachineDataStreamPort {

    private String topic = "machine-data-stream";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void streamMachineData(HashMap event) {
        kafkaTemplate.send(topic, "machine-01", event);
    }
}
