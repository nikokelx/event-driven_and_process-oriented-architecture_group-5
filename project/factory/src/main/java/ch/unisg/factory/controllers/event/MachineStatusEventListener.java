package ch.unisg.factory.controllers.event;

import ch.unisg.factory.core.entities.Machine;
import ch.unisg.factory.core.ports.in.ToggleMachineStatusCommand;
import ch.unisg.factory.core.ports.in.ToggleMachineStatusUseCase;
import ch.unisg.factory.infrastructure.repository.MachineRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MachineStatusEventListener {
    /*
    @Value("${spring.kafka.topic-custom-log}")
    private String topicCustomLog;

     */

    private final ToggleMachineStatusUseCase toggleMachineStatusUseCase;

    @KafkaListener(topics = "machine-status", containerFactory = "kafkaListenerStringFactory")
    public void consumeMessage(String message) {
        // kafkaTemplate.send(topicCustomLog, "Receive Event: Machine Status toggle.");

        System.out.println(message);

        Machine.MachineStatus machineStatus;

        if (message.equals("ACTIVE")) {
            System.out.println("Machine Status active.");
            machineStatus = new Machine.MachineStatus(true);
        } else {
            System.out.println("Machine Status inactive.");
            machineStatus = new Machine.MachineStatus(false);
        }

        ToggleMachineStatusCommand command = new ToggleMachineStatusCommand(
                machineStatus
        );

        toggleMachineStatusUseCase.toggleMachineStatus(command);
    }

}
