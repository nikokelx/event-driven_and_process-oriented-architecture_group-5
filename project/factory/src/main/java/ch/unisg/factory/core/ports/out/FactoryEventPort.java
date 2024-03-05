package ch.unisg.factory.core.ports.out;

import ch.unisg.factory.core.entities.Factory;
import ch.unisg.factory.core.entities.Machine;

public interface FactoryEventPort {
    int publishFactoryInventoryLevel(Factory.FactoryInventoryLevel factoryInventoryLevel);
}
