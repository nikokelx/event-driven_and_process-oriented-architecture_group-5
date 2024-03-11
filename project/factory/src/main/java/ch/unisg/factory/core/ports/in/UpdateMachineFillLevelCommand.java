package ch.unisg.factory.core.ports.in;

import ch.unisg.factory.core.entities.Machine;
import lombok.Value;

@Value
public class UpdateMachineFillLevelCommand {

    private final Machine.MachineFillLevel machineFillLevel;

    public UpdateMachineFillLevelCommand(Machine.MachineFillLevel machineFillLevel) {
        this.machineFillLevel = machineFillLevel;
    }
}
