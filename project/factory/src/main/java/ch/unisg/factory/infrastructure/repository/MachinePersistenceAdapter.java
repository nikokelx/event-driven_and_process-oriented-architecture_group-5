package ch.unisg.factory.infrastructure.repository;

import ch.unisg.factory.core.entities.Machine;
import ch.unisg.factory.core.entities.MachineRepository;
import ch.unisg.factory.core.ports.out.MachineStatusEventPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Primary
@RequiredArgsConstructor
public class MachinePersistenceAdapter implements MachineStatusEventPort {

    private final MachineRepositoryAdapter machineRepositoryAdapter;

    @Override
    public void toggleMachineStatus(Machine.MachineStatus machineStatus) {
        
        machineRepositoryAdapter.setMachineStatusById(0, machineStatus.getValue());

    }
}
