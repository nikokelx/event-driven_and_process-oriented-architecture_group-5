package ch.unisg.warehouse.core.services;

import ch.unisg.warehouse.core.ports.in.UpdateFactoryInventoryLevelCommand;
import ch.unisg.warehouse.core.ports.in.UpdateFactoryInventoryLevelUseCase;
import ch.unisg.warehouse.core.ports.out.UpdateFactoryInventoryLevelPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateFactoryInventoryLevel implements UpdateFactoryInventoryLevelUseCase {

    private final UpdateFactoryInventoryLevelPort updateFactoryInventoryLevelPort;

    @Override
    public void updateFactoryInventoryLevel(UpdateFactoryInventoryLevelCommand command) {

        updateFactoryInventoryLevelPort.updateFactoryInventoryLevel(command.getFactoryInventoryLevel());

    }
}
