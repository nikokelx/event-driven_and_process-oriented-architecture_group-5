package ch.unisg.machines.core.services;

import ch.unisg.machines.core.entities.Machine;
import ch.unisg.machines.core.ports.in.CollectMachineFillLevelUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CollectMachineFillLevel implements CollectMachineFillLevelUseCase {

    Machine machine = Machine.getMachine();

    @Override
    public Machine.MachineFillLevel collectMachineFillLevel() {

        // get machine fill level
        Machine.MachineFillLevel machineFillLevel = machine.getMachineFillLevel();

        // set machine fill level to zero
        machine.setMachineFillLevel(new Machine.MachineFillLevel(0));

        // return machine fill level
        return machineFillLevel;
    }
}
