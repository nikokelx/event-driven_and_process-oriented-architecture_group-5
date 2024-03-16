package ch.unisg.factory.core.services;

import ch.unisg.factory.core.entities.Factory;
import ch.unisg.factory.core.entities.Machine;
import ch.unisg.factory.core.ports.in.UpdateMachineFillLevelCommand;
import ch.unisg.factory.core.ports.in.UpdateMachineFillLevelUseCase;
import ch.unisg.factory.core.ports.out.CollectMachineFillLevelPort;
import ch.unisg.factory.core.ports.out.PublishFactoryInventoryLevelPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateMachineFillLevel implements UpdateMachineFillLevelUseCase {

    private final CollectMachineFillLevelPort collectMachineFillLevelPort;


    private final PublishFactoryInventoryLevelPort publishFactoryInventoryLevelPort;

    Machine.MachineFillLevel machineFillLevel;

    @Override
    public void updateMachineFillLevel(UpdateMachineFillLevelCommand command) {

        if (command.getMachineFillLevel().getValue() >= 10) {

            // Get goods from the machine
            int fillLevel = collectMachineFillLevelPort.collectMachineFillLevel();
            Factory.FactoryInventoryLevel factoryInventoryLevel = new Factory.FactoryInventoryLevel(fillLevel);

            // Emit event
            publishFactoryInventoryLevelPort.publishFactorcyInventoryLevel(factoryInventoryLevel);

        }

    }

}
