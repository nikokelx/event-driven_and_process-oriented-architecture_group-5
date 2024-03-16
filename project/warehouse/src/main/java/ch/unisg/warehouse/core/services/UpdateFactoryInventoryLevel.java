package ch.unisg.warehouse.core.services;

import ch.unisg.warehouse.core.ports.in.UpdateFactoryInventoryLevelCommand;
import ch.unisg.warehouse.core.ports.in.UpdateFactoryInventoryLevelUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateFactoryInventoryLevel implements UpdateFactoryInventoryLevelUseCase {

    @Override
    public void updateFactoryInventoryLevel(UpdateFactoryInventoryLevelCommand command) {

        System.out.println(command.getFactoryInventoryLevel().getValue());

    }
}
