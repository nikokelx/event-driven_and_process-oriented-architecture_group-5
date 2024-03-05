package ch.unisg.factory.controllers.event;

import ch.unisg.factory.core.entities.Machine;
import ch.unisg.factory.core.ports.in.UpdateFactoryInventoryLevelCommand;
import ch.unisg.factory.core.ports.in.UpdateFactoryInventoryLevelUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MachineFillLevelEventListener {

    private final UpdateFactoryInventoryLevelUseCase updateFactoryInventoryLevelUseCase;

    @KafkaListener(topics = "machineFillLevel", containerFactory = "kafkaListenerStringFactory")
    public void consumeFillLevel(String message) {

        Machine.MachineFillLevel machineFillLevel = new Machine.MachineFillLevel(Integer.valueOf(message));
        UpdateFactoryInventoryLevelCommand command = new UpdateFactoryInventoryLevelCommand(machineFillLevel);

        int updateMachineFillLevel = updateFactoryInventoryLevelUseCase.updateMachineFillLevel(command);

    }
}
