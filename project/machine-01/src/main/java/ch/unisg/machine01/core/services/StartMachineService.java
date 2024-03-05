package ch.unisg.machine01.core.services;

import ch.unisg.machine01.core.entities.Machine;
import ch.unisg.machine01.core.ports.in.StartMachineCommand;
import ch.unisg.machine01.core.ports.in.StartMachineUseCase;
import ch.unisg.machine01.core.ports.out.MachineStatusEventPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartMachineService implements StartMachineUseCase {

    private final MachineStatusEventPort machineStatusEventPort;

    @Override
    public Machine.MachineStatus startMachine(StartMachineCommand command) {

        Machine machine = new Machine();

        machineStatusEventPort.toggleMachineStatus(machine.getMachineStatus());
        System.out.println("1");

        return machine.getMachineStatus();
    }

}
