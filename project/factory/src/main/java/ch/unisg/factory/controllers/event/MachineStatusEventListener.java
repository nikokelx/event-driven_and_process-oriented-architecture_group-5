package ch.unisg.factory.controllers.event;

import ch.unisg.factory.core.entities.Machine;
import ch.unisg.factory.core.ports.in.ToggleMachineStatusCommand;
import ch.unisg.factory.core.ports.in.ToggleMachineStatusUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MachineStatusEventListener {

    private final ToggleMachineStatusUseCase toggleMachineStatusUseCase;

    @KafkaListener(topics = "machine-status", containerFactory = "kafkaListenerStringFactory")
    public void consumeMessage(@Payload String message, @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key) {

        Machine.MachineStatus machineStatus;

        // log machine status
        if (message.equals("ACTIVE")) {
            System.out.println("Machine Status active.");
            machineStatus = new Machine.MachineStatus(true);
        } else {
            System.out.println("Machine Status inactive.");
            machineStatus = new Machine.MachineStatus(false);
        }

        // create a command
        ToggleMachineStatusCommand command = new ToggleMachineStatusCommand(
                machineStatus
        );

        // execute command to toggle the machine status
        toggleMachineStatusUseCase.toggleMachineStatus(command);
    }

}
