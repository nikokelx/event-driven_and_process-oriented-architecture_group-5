package ch.unisg.factory.core.services;

import ch.unisg.factory.core.ports.in.UpdateMachineFillLevelCommand;
import ch.unisg.factory.core.ports.in.UpdateMachineFillLevelUseCase;
import ch.unisg.factory.core.ports.out.MachineFillLevelEventPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateMachineFillLevel implements UpdateMachineFillLevelUseCase {

    private final MachineFillLevelEventPort machineFillLevelEventPort;

    @Override
    public void updateMachineFillLevel(UpdateMachineFillLevelCommand command) {

        machineFillLevelEventPort.updateMachineFillLevelEvent(command.getMachineFillLevel());

    }

}
