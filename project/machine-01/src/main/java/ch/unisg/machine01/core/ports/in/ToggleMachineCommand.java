package ch.unisg.machine01.core.ports.in;

import ch.unisg.machine01.core.entities.Machine;
import lombok.NonNull;
import lombok.Value;

@Value
public class ToggleMachineCommand {

    @NonNull
    private final Machine.MachineProductionSpeed machineProductionSpeed;

    public ToggleMachineCommand(
            Machine.MachineProductionSpeed machineProductionSpeed
    ) {
        this.machineProductionSpeed = machineProductionSpeed;
    }
}
