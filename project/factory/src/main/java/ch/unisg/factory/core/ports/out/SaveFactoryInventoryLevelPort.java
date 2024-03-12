package ch.unisg.factory.core.ports.out;

import ch.unisg.factory.core.entities.Factory;

public interface SaveFactoryInventoryLevelPort {
    void saveFactoryInventoryLevel(Factory.FactoryInventoryLevel factoryInventoryLevel);
}
