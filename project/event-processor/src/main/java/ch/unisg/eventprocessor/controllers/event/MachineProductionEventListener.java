package ch.unisg.eventprocessor.controllers.event;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MachineProductionEventListener {

    /*

        TEST CONTROLLER

     */

    @KafkaListener(topics = "stream-machine-fill-level", containerFactory = "kafkaListenerObjectFactory")
    public void consumeStreamMachineFillLevel(String stream) {

        // System.out.println(stream);

    }

}
