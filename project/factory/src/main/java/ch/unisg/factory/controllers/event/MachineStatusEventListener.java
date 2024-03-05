package ch.unisg.factory.controllers.event;

import ch.unisg.factory.infrastructure.repository.MachineRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MachineStatusEventListener {

    @Autowired
    private MachineRepositoryAdapter repository;

    private final JdbcTemplate jdbcTemplate;

    @KafkaListener(topics = "machineStatus", containerFactory = "kafkaListenerStringFactory")
    public void consumeMessage(String message) {
        System.out.println(message);
    }

}
