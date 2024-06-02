package ch.unisg.factory.controllers.event;

import ch.unisg.factory.core.entities.Machine;
import ch.unisg.factory.core.entities.Outbox;
import ch.unisg.factory.core.ports.in.UpdateMachineFillLevelCommand;
import ch.unisg.factory.core.ports.in.UpdateMachineFillLevelUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MachineFillLevelEventListener {

    private final UpdateMachineFillLevelUseCase updateMachineFillLevelUseCase;

    @KafkaListener(topics = "machine-fill-level", containerFactory = "kafkaListenerStringFactory")
    public void consumeFillLevel(String message) {

        System.out.println(message);

        // create a command with the machine fill level
        Machine.MachineFillLevel machineFillLevel = new Machine.MachineFillLevel(Double.parseDouble(message));
        UpdateMachineFillLevelCommand command = new UpdateMachineFillLevelCommand(machineFillLevel);

        // execute command to update the machine fill level
        updateMachineFillLevelUseCase.updateMachineFillLevel(command);

    }
}
