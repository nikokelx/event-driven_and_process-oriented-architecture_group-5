package ch.unisg.factory.core.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

public class Factory {

    @Getter @Setter
    private FactoryInventoryLevel factoryInventoryLevel;

    public Factory(
            Factory.FactoryInventoryLevel factoryInventoryLevel
    ) {
        this.factoryInventoryLevel = factoryInventoryLevel;
    }

    @Value
    public static class FactoryInventoryLevel {
        int value;
    }
}
