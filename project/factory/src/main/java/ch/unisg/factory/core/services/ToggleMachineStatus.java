package ch.unisg.factory.core.services;

import ch.unisg.factory.core.entities.Machine;
import ch.unisg.factory.core.ports.in.ToggleMachineStatusCommand;
import ch.unisg.factory.core.ports.in.ToggleMachineStatusUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class ToggleMachineStatus implements ToggleMachineStatusUseCase {

    private Machine machine = Machine.getMachine();
    @Override
    public void toggleMachineStatus(ToggleMachineStatusCommand command) {

        machine.setMachineStatus(new Machine.MachineStatus(command.getMachineStatus().getValue()));
    }
}
