package ch.unisg.factory.infrastructure.adapters.messages;

import ch.unisg.factory.core.entities.Factory;
import ch.unisg.factory.core.ports.out.PublishFactoryInventoryLevelPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Primary
@RequiredArgsConstructor
public class PublishFactoryInventoryLevelMessage implements PublishFactoryInventoryLevelPort {

    @Value("${spring.kafka.topic-factory-inventory-level}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publishFactorcyInventoryLevel(Factory.FactoryInventoryLevel factoryInventoryLevel) {
        kafkaTemplate.send(topic, String.valueOf(factoryInventoryLevel.getValue()));
    }
}
