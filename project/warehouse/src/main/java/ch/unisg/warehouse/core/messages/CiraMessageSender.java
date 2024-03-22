package ch.unisg.warehouse.core.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Helper to send messages, via Kafka, to 'cira-production' topic only.
 */
@Component
public class CiraMessageSender {

    public static final String TOPIC_NAME = "cira-production";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public NewTopic autoCreateTopicOnStartupIfNotExistent() {
        return TopicBuilder.name(TOPIC_NAME).partitions(1).replicas(1).build();
    }

    public void send(Message<?> m) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(m);

            ProducerRecord<String, String> record = new ProducerRecord<String, String>(TOPIC_NAME, jsonMessage);
            record.headers().add("type", m.getType().getBytes());

            kafkaTemplate.send(record);

            System.out.println("Sending " + m.getType() + " message: " + m.getData().toString() + " to " + TOPIC_NAME);
        } catch (Exception e) {
            throw new RuntimeException("Could not transform and send message: "+ e.getMessage(), e);
        }
    }
}
