package ch.unisg.factory.controllers.event;

import ch.unisg.factory.core.entities.Machine;
import ch.unisg.factory.core.ports.in.UpdateMachineFillLevelCommand;
import ch.unisg.factory.core.ports.in.UpdateMachineFillLevelUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MachineFillLevelEventListener {

    private final UpdateMachineFillLevelUseCase updateMachineFillLevelUseCase;

    @KafkaListener(topics = "machineFillLevel", containerFactory = "kafkaListenerStringFactory")
    public void consumeFillLevel(String message) {

        System.out.println(message);

        Machine.MachineFillLevel machineFillLevel = new Machine.MachineFillLevel(Integer.valueOf(message));
        UpdateMachineFillLevelCommand command = new UpdateMachineFillLevelCommand(machineFillLevel);

        updateMachineFillLevelUseCase.updateMachineFillLevel(command);

    }
}
