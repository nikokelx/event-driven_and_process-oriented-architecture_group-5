package ch.unisg.warehouse.core.ports.in;

import ch.unisg.warehouse.core.entities.Factory;
import lombok.NonNull;
import lombok.Value;

@Value
public class UpdateFactoryInventoryLevelCommand {

    @NonNull
    private final Factory.FactoryInventoryLevel factoryInventoryLevel;

    public UpdateFactoryInventoryLevelCommand(
            Factory.FactoryInventoryLevel factoryInventoryLevel
    ) {
        this.factoryInventoryLevel = factoryInventoryLevel;
    }

}
