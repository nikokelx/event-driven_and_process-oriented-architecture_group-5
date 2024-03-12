package ch.unisg.machine01.core.services;

import ch.unisg.machine01.core.entities.Machine;
import ch.unisg.machine01.core.ports.in.CollectMachineFillLevelUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CollectMachineFillLevel implements CollectMachineFillLevelUseCase {

    Machine machine = Machine.getMachine();

    @Override
    public Machine.MachineFillLevel collectMachineFillLevel() {

        Machine.MachineFillLevel machineFillLevel = machine.getMachineFillLevel();
        machine.setMachineFillLevel(0);

        return machineFillLevel;
    }
}
