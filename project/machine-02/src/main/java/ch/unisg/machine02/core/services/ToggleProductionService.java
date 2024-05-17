package ch.unisg.machine02.core.services;

import ch.unisg.machine02.core.entities.Machine;
import ch.unisg.machine02.core.entities.MachineData;
import ch.unisg.machine02.core.ports.in.ToggleProductionCommand;
import ch.unisg.machine02.core.ports.in.ToggleProductionUseCase;
import ch.unisg.machine02.core.ports.out.FillLevelEventPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
public class ToggleProductionService implements ToggleProductionUseCase {

    private final FillLevelEventPort fillLevelEventPort;

    // Get Machine
    Machine machine = Machine.getMachine();
    Machine.MachineProductionStatus machineProductionStatus = machine.getMachineProductionStatus();
    private final Machine.ProductionThread productionThread = new Machine.ProductionThread(2000);

    private class EventThread implements Runnable {
        private Thread worker;
        private final AtomicBoolean running = new AtomicBoolean(false);
        private int interval;

        public EventThread(int sleepInterval) {
            interval = sleepInterval;
        }

        public void start() {
            worker = new Thread(this);
            worker.start();
        }

        public void stop() {
            running.set(false);
        }

        public void run() {

            running.set(true);

            while (running.get()) {

                try {
                    Thread.sleep(interval);

                    MachineData machineData = new MachineData(
                            new MachineData.MachineId(machine.getMachineId().getValue()),
                            new MachineData.MachineProductionPerSecond(machine.getMachineLastIncrease().getValue()),
                            new MachineData.MachineFillLevel(machine.getMachineFillLevel().getValue())
                    );

                    fillLevelEventPort.publishFillLevel(machine.getMachineFillLevel());
                    fillLevelEventPort.publishMachineData(machineData);

                    if (machine.getMachineFillLevel().getValue() == machine.getMachineCapacity().getValue()) {
                        this.stop();
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

            }
        }

    }

    // Init the Thread
    EventThread eventThread = new EventThread(4352);

    @Override
    public int toggleProduction(ToggleProductionCommand command) {

        // If production is running, stop the production. Otherwise, start the production.
        machineProductionStatus.toggle();

        if (machineProductionStatus.getMachineProductionStatus()) {
            productionThread.start();
            eventThread.start();
        } else {
            productionThread.stop();
            eventThread.stop();
        }

        return 0;
    }
}
