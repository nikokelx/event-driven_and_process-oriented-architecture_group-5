package ch.unisg.warehouse.core.ports.in;

import ch.unisg.warehouse.core.entities.Factory;
import lombok.NonNull;
import lombok.Value;

@Value
public class UpdateFactoryInventoryLevelCommand {

    @NonNull
    private final Factory.InventoryLevel factoryInventoryLevel;

    public UpdateFactoryInventoryLevelCommand(Factory.InventoryLevel factoryInventoryLevel) {
        this.factoryInventoryLevel = factoryInventoryLevel;
    }
}
