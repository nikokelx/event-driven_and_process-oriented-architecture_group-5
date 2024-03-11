package ch.unisg.machine01.core.services;

import ch.unisg.machine01.core.entities.Machine;
import ch.unisg.machine01.core.ports.in.ToggleMachineCommand;
import ch.unisg.machine01.core.ports.in.ToggleMachineUseCase;
import ch.unisg.machine01.core.ports.out.MachineStatusEventPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ToggleMachineService implements ToggleMachineUseCase {

    private final MachineStatusEventPort machineStatusEventPort;

    @Override
    public Machine.MachineStatus toggleMachine(ToggleMachineCommand command) {

        Machine machine = Machine.getMachine();
        Machine.MachineStatus machineStatus = machine.getMachineStatus();

        System.out.println("Old Machine status: " + machineStatus.getValue().toString());

        machineStatus = machine.toggleStatus();

        System.out.println("New machine status: " + machineStatus.getValue().toString());

        machineStatusEventPort.toggleMachineStatus(machineStatus);

        return machineStatus;
    }

}
