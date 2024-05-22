package ch.unisg.factory.controllers.event.streams;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StreamListener {

    @KafkaListener(topics = "machine-stream", containerFactory = "kafkaListenerObjectFactory")
    public void consumeStream(String message) {
        System.out.println(message);
    }
}
