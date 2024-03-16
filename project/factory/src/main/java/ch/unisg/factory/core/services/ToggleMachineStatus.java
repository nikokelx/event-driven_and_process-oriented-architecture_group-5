package ch.unisg.factory.core.services;

import ch.unisg.factory.core.ports.in.ToggleMachineStatusCommand;
import ch.unisg.factory.core.ports.in.ToggleMachineStatusUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ToggleMachineStatus implements ToggleMachineStatusUseCase {

    @Override
    public void toggleMachineStatus(ToggleMachineStatusCommand command) {
        System.out.println(command.getMachineStatus().getValue().toString());
        //
    }
}
