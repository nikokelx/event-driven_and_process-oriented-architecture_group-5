package ch.unisg.warehouse.controllers.event;

import ch.unisg.warehouse.core.entities.Factory;
import ch.unisg.warehouse.core.ports.in.UpdateFactoryInventoryLevelCommand;
import ch.unisg.warehouse.core.ports.in.UpdateFactoryInventoryLevelUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FactoryInventoryLevelListener {

    private final UpdateFactoryInventoryLevelUseCase updateFactoryInventoryLevelUseCase;

    @KafkaListener(topics = "factoryInventoryLevel", containerFactory = "kafkaListenerStringFactory")
    public void consumeFactoryInventoryLevel(String message) {
        Factory.FactoryInventoryLevel factoryInventoryLevel = new Factory.FactoryInventoryLevel(Integer.valueOf(message));
        UpdateFactoryInventoryLevelCommand command = new UpdateFactoryInventoryLevelCommand(factoryInventoryLevel);

        updateFactoryInventoryLevelUseCase.updateFactoryInventoryLevel(command);

    }

}
