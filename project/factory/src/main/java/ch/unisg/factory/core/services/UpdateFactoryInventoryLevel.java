package ch.unisg.factory.core.services;

import ch.unisg.factory.core.entities.Factory;
import ch.unisg.factory.core.ports.in.UpdateFactoryInventoryLevelCommand;
import ch.unisg.factory.core.ports.in.UpdateFactoryInventoryLevelUseCase;
import ch.unisg.factory.core.ports.out.FactoryEventPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateFactoryInventoryLevel implements UpdateFactoryInventoryLevelUseCase {

    private final FactoryEventPort factoryEventPort;

    @Override
    public int updateMachineFillLevel(UpdateFactoryInventoryLevelCommand command) {

        Factory factory = new Factory(new Factory.FactoryInventoryLevel(command.getMachineFillLevel().getValue()));
        Factory.FactoryInventoryLevel factoryInventoryLevel = factory.getFactoryInventoryLevel();

        if (factoryInventoryLevel.getValue() > 80) {
            factoryEventPort.publishFactoryInventoryLevel(factoryInventoryLevel);
        }

        return 0;
    }

}
