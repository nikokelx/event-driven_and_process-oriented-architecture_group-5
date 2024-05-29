package ch.unisg.machine02.core.services;

import ch.unisg.machine02.core.entities.Machine;
import ch.unisg.machine02.core.ports.in.ToggleMachineCommand;
import ch.unisg.machine02.core.ports.in.ToggleMachineUseCase;
import ch.unisg.machine02.core.ports.out.MachineStatusEventPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ToggleMachineService implements ToggleMachineUseCase {

    private final MachineStatusEventPort machineStatusEventPort;

    Machine machine = Machine.getMachine();

    @Override
    public Machine.MachineStatus toggleMachine(ToggleMachineCommand command) {

        Machine.MachineStatus machineStatus = machine.toggleStatus();

        if ("ACTIVE".equals(machineStatus.getValue().toString())) {
            System.out.println("Machine is on.");
        } else {
            System.out.println("Machine is off");
        }

        machineStatusEventPort.toggleMachineStatus(machineStatus);

        return machineStatus;
    }

}
