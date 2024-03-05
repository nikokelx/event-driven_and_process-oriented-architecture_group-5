package ch.unisg.factory.core.ports.in;

import ch.unisg.factory.core.entities.Machine;
import lombok.Value;

@Value
public class UpdateFactoryInventoryLevelCommand {

    private final Machine.MachineFillLevel machineFillLevel;

    public UpdateFactoryInventoryLevelCommand(Machine.MachineFillLevel machineFillLevel) {
        this.machineFillLevel = machineFillLevel;
    }
}
