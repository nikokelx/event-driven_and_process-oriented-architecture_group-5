package ch.unisg.eventprocessor.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaConfig {
    @Bean
    public NewTopic machineFillStreamsTopic() {
        return TopicBuilder.name("stream-machine-fill-level").build();
    }

    @Bean
    public NewTopic machineProductionStreamsTopic() {
        return TopicBuilder.name("stream-machine-production").build();
    }

    @Bean
    public NewTopic machineTemperatureStreamsTopic() {
        return TopicBuilder.name("stream-machine-temperature").build();
    }
}
