package ch.unisg.warehouse.core.entities;

import lombok.Value;

public class Factory {

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
