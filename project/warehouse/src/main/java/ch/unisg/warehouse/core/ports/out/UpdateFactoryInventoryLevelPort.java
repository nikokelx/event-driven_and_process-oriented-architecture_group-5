package ch.unisg.warehouse.core.ports.out;

import ch.unisg.warehouse.core.entities.Factory;

public interface UpdateFactoryInventoryLevelPort {

    void updateFactoryInventoryLevel(Factory.FactoryInventoryLevel factoryInventoryLevel);
}
