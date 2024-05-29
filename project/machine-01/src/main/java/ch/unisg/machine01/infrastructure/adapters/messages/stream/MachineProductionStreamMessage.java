package ch.unisg.machine01.infrastructure.adapters.messages.stream;

import ch.unisg.machine01.core.entities.Machine;
import ch.unisg.machine01.core.ports.out.streams.FillLevelStreamPort;
import ch.unisg.machine01.core.ports.out.streams.ProductionStreamPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

@Primary
@Component
@RequiredArgsConstructor
public class MachineProductionStreamMessage implements ProductionStreamPort {

    @Value("${spring.kafka.topic-stream-machine-production}")
    private String topic;

    @Autowired
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private Machine machine = Machine.getMachine();

    @Override
    public void streamProduction(Machine.MachineLastIncrease machineLastIncrease) {

        // emit events
        HashMap<String, String> event = new HashMap();

        Instant now = Instant.now();

        // Format the timestamp to the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
                .withZone(ZoneId.of("UTC"));

        String timestamp = formatter.format(now);

        // initialize event
        event.put("timestamp", timestamp.toString());
        event.put("production", String.valueOf(machineLastIncrease.getValue()));

        kafkaTemplate.send(topic, machine.getMachineId().getValue(), event);
    }
}
