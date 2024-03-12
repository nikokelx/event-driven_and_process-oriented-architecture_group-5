package ch.unisg.factory.core.services;

import ch.unisg.factory.core.entities.Factory;
import ch.unisg.factory.core.entities.Machine;
import ch.unisg.factory.core.ports.in.UpdateMachineFillLevelCommand;
import ch.unisg.factory.core.ports.in.UpdateMachineFillLevelUseCase;
import ch.unisg.factory.core.ports.out.CollectMachineFillLevelPort;
import ch.unisg.factory.core.ports.out.MachineFillLevelEventPort;
import ch.unisg.factory.core.ports.out.PublishFactoryInventoryLevelPort;
import ch.unisg.factory.core.ports.out.SaveFactoryInventoryLevelPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateMachineFillLevel implements UpdateMachineFillLevelUseCase {

    private final MachineFillLevelEventPort machineFillLevelEventPort;

    private final CollectMachineFillLevelPort collectMachineFillLevelPort;

    private final SaveFactoryInventoryLevelPort saveFactoryInventoryLevelPort;

    private final PublishFactoryInventoryLevelPort publishFactoryInventoryLevelPort;

    Machine.MachineFillLevel machineFillLevel;

    @Override
    public void updateMachineFillLevel(UpdateMachineFillLevelCommand command) {

        if (command.getMachineFillLevel().getValue() >= 10) {

            // Get goods from the machine
            int fillLevel = collectMachineFillLevelPort.collectMachineFillLevel();
            Factory.FactoryInventoryLevel factoryInventoryLevel = new Factory.FactoryInventoryLevel(fillLevel);

            // Put goods in the inventory
            saveFactoryInventoryLevelPort.saveFactoryInventoryLevel(factoryInventoryLevel);

            // Emit event
            publishFactoryInventoryLevelPort.publishFactorcyInventoryLevel(factoryInventoryLevel);

        }

        machineFillLevelEventPort.updateMachineFillLevelEvent(command.getMachineFillLevel());

    }

}
