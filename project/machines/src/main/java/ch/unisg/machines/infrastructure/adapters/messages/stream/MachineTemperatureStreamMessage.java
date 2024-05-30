package ch.unisg.machines.infrastructure.adapters.messages.stream;

import ch.unisg.machines.core.entities.Machine;
import ch.unisg.machines.core.ports.out.streams.TemperatureStreamPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Component
@Primary
@RequiredArgsConstructor
public class MachineTemperatureStreamMessage implements TemperatureStreamPort {

    @Value("${spring.kafka.topic-stream-machine-temperature}")
    private String topic;

    @Autowired
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private Machine machine = Machine.getMachine();

    @Override
    public void streamTemperature(Machine.MachineTemperature machineTemperature) {
        // emit events
        HashMap<String, String> event = new HashMap();

        Instant now = Instant.now();

        // Format the timestamp to the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
                .withZone(ZoneId.of("UTC"));

        String timestamp = formatter.format(now);

        // initialize event
        event.put("timestamp", timestamp.toString());
        event.put("temperature", String.valueOf(machineTemperature.getValue()));

        kafkaTemplate.send(topic, machine.getMachineId().getValue(), event);
    }
}
