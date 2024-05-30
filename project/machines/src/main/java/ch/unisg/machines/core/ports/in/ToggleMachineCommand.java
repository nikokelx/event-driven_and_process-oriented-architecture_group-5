package ch.unisg.machines.core.ports.in;

import ch.unisg.machines.core.entities.Machine;
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
