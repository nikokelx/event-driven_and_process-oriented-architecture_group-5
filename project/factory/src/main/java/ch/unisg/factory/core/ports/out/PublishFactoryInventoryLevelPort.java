package ch.unisg.factory.core.ports.out;

import ch.unisg.factory.core.entities.Factory;

public interface PublishFactoryInventoryLevelPort {
    void publishFactorcyInventoryLevel(Factory.FactoryInventoryLevel factoryInventoryLevel);
}
