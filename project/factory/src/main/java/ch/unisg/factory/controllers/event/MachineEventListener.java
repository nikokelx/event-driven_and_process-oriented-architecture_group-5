package ch.unisg.factory.controllers.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MachineEventListener {

    @KafkaListener(topics = "machineStatus", containerFactory = "kafkaListenerStringFactory")
    public void consumeMessage(String message) {
        System.out.println(message);
    }

}
