package ch.unisg.factory.controllers.event.streams;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StreamListener {

    @KafkaListener(topics = "machine-stream", containerFactory = "kafkaListenerObjectFactory")
    public void consumeStream(ConsumerRecord<String, String> record) {
        System.out.println(record.key() + ": " + record.value());
    }
}
