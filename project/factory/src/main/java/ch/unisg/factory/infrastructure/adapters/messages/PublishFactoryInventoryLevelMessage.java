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

    @Value("${spring.kafka.topic-inv_level}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publishFactoryInventoryLevel(Factory.InventoryLevel factoryInventoryLevel) {
        Factory factory = Factory.getFactory();
        kafkaTemplate.send(topic, String.valueOf(factory.getInventoryLevel().value()));
    }
}
