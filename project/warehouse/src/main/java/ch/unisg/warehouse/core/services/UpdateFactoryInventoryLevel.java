package ch.unisg.warehouse.core.services;

import ch.unisg.warehouse.core.entities.Factory;
import ch.unisg.warehouse.core.ports.in.UpdateFactoryInventoryLevelCommand;
import ch.unisg.warehouse.core.ports.in.UpdateFactoryInventoryLevelUseCase;
import org.springframework.stereotype.Service;

@Service("UpdateFactoryInventoryLevel")
public class UpdateFactoryInventoryLevel implements UpdateFactoryInventoryLevelUseCase {
    @Override
    public void updateFactoryInventoryLevel(UpdateFactoryInventoryLevelCommand command) {
        Factory factory = Factory.getFactory();
        factory.setInventoryLevel(command.getFactoryInventoryLevel());
        System.out.println("Warehouse -> Updated inventory level to { " + command.getFactoryInventoryLevel() + " }");
    }
}
