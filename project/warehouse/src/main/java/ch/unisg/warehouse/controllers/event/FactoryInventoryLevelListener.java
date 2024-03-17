package ch.unisg.warehouse.controllers.event;

import ch.unisg.warehouse.core.entities.Factory;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FactoryInventoryLevelListener {

    @KafkaListener(topics = "factory-inventory-level", containerFactory = "kafkaListenerStringFactory")
    public void consumeFactoryInventoryLevel(String message) {
        Factory factory = Factory.getFactory();
        factory.increaseInventoryLevel(Integer.parseInt(message));
    }
}
