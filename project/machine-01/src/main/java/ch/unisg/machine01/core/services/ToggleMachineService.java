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

    Machine machine = Machine.getMachine();

    @Override
    public Machine.MachineStatus toggleMachine(ToggleMachineCommand command) {

        Machine.MachineStatus machineStatus = machine.toggleStatus();

        machineStatusEventPort.toggleMachineStatus(machineStatus);

        return machineStatus;
    }

}
