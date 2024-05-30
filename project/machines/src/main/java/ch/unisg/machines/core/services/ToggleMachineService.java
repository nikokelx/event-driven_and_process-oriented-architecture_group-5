package ch.unisg.machines.core.services;

import ch.unisg.machines.core.entities.Machine;
import ch.unisg.machines.core.ports.in.ToggleMachineCommand;
import ch.unisg.machines.core.ports.in.ToggleMachineUseCase;
import ch.unisg.machines.core.ports.out.MachineStatusEventPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ToggleMachineService implements ToggleMachineUseCase {

    // retrieve the outgoing port
    private final MachineStatusEventPort machineStatusEventPort;

    // retrieve the machine (singleton)
    Machine machine = Machine.getMachine();

    @Override
    public Machine.MachineStatus toggleMachine(ToggleMachineCommand command) {

        // retrieve machine production speed
        Machine.MachineProductionSpeed machineProductionSpeed = command.getMachineProductionSpeed();

        // configure machine production speed
        machine.setMachineProductionSpeed(machineProductionSpeed);

        // toggle the machine (on/off)
        Machine.MachineStatus machineStatus = machine.toggleStatus();

        // print the current status of the machine (debugging)
        if ("ACTIVE".equals(machineStatus.getValue().toString())) {
            System.out.println("Machine is on.");
        } else {
            System.out.println("Machine is off");
        }

        // execute a method from the outgoing port
        machineStatusEventPort.toggleMachineStatus(machineStatus);

        // return the machine status
        return machineStatus;
    }

}
