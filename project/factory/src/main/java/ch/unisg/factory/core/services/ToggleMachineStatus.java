package ch.unisg.factory.core.services;

import ch.unisg.factory.core.ports.in.ToggleMachineStatusCommand;
import ch.unisg.factory.core.ports.in.ToggleMachineStatusUseCase;
import ch.unisg.factory.core.ports.out.MachineStatusEventPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ToggleMachineStatus implements ToggleMachineStatusUseCase {

    private final MachineStatusEventPort machineStatusEventPort;

    @Override
    public void toggleMachineStatus(ToggleMachineStatusCommand command) {
        machineStatusEventPort.toggleMachineStatus(command.getMachineStatus());

    }
}
