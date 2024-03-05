package ch.unisg.machine01.core.services;

import ch.unisg.machine01.core.entities.Machine;
import ch.unisg.machine01.core.ports.in.StartProductionCommand;
import ch.unisg.machine01.core.ports.in.StartProductionUseCase;
import ch.unisg.machine01.core.ports.out.FillLevelEventPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartProductionService implements StartProductionUseCase {

    private final FillLevelEventPort fillLevelEventPort;

    @Override
    public int startProduction(StartProductionCommand command) {

        Machine.MachineFillLevel machineFillLevel = new Machine.MachineFillLevel(0);

        Thread productionThread = new Thread(() -> {
            fillLevelEventPort.publishFillLevel(machineFillLevel);
        });

        productionThread.start();

        return 0;
    }
}
