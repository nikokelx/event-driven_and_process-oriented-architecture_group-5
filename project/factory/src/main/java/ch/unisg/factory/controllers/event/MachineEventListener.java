package ch.unisg.factory.controllers.event;

import ch.unisg.factory.core.entities.MachineRepository;
import ch.unisg.factory.infrastructure.repository.MachineDBRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.sql.Driver;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MachineEventListener {

    @Autowired
    private MachineDBRepository repository;

    private final JdbcTemplate jdbcTemplate;

    @KafkaListener(topics = "machineStatus", containerFactory = "kafkaListenerStringFactory")
    public void consumeMessage(String message) {
        System.out.println(message);
    }

}
