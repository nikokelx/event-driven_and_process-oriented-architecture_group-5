package ch.unisg.factory.core.ports.in;

import ch.unisg.factory.core.entities.Machine;
import lombok.NonNull;
import lombok.Value;

@Value
public class ToggleMachineStatusCommand {

    @NonNull
    private final Machine.MachineStatus machineStatus;

    public ToggleMachineStatusCommand(
            Machine.MachineStatus machineStatus
    ) {
        this.machineStatus = machineStatus;
    }
}
