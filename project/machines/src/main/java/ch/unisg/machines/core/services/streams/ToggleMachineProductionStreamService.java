package ch.unisg.machines.core.services.streams;

import ch.unisg.machines.core.entities.Machine;
import ch.unisg.machines.core.ports.in.streams.ToggleMachineProductionStreamUseCase;
import ch.unisg.machines.core.ports.out.streams.FillLevelStreamPort;
import ch.unisg.machines.core.ports.out.streams.ProductionStreamPort;
import ch.unisg.machines.core.ports.out.streams.TemperatureStreamPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
public class ToggleMachineProductionStreamService implements ToggleMachineProductionStreamUseCase {

    // retrieve outgoing ports
    private final FillLevelStreamPort fillLevelStreamPort;
    private final ProductionStreamPort productionStreamPort;
    private final TemperatureStreamPort temperatureStreamPort;


    // retrieve the machine
    Machine machine = Machine.getMachine();

    private class EventThread implements Runnable {

        private Thread worker;
        private final AtomicBoolean running = new AtomicBoolean(false);
        private int interval;

        public EventThread(int sleepInterval) {
            this.interval = sleepInterval;
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

            Machine machine = Machine.getMachine();
            System.out.println("EVENT DOES IT START?");
            while (running.get()) {
                System.out.println("EVENT 1");
                try {
                    System.out.println("EVENT 2");
                    Thread.sleep(this.interval);

                    // emit three data streams
                    fillLevelStreamPort.streamFillLevel(machine.getMachineFillLevel());
                    productionStreamPort.streamProduction(machine.getMachineLastIncrease());
                    temperatureStreamPort.streamTemperature(machine.getMachineTemperature());

                    System.out.println("EvENT 3");
                    // if the capacity is reached stop the event thread
                    if (machine.getMachineFillLevel().getValue() == machine.getMachineCapacity().getValue()) {
                        this.stop();
                    }

                } catch (InterruptedException e) {

                    System.out.println("EVENT ERROR");
                    Thread.currentThread().interrupt();
                }
            }
        }

    }

    // Create a new eventThread
    private EventThread eventThread = new EventThread(4000);

    // create a new production thread
    private Machine.ProductionThread productionThread = new Machine.ProductionThread(4000);

    @Override
    public int toggleMachineProductionStream() {

        // get machine production status
        machine.getMachineProductionStatus().toggle();

        System.out.println(machine.getMachineProductionStatus().getValue());
        // if machine production status is off
        if (machine.getMachineProductionStatus().getValue()) {
            System.out.println("HERE ; 0");
            // start the production and event streams
            productionThread.start();
            eventThread.start();
            return 0;
        } else {
            System.out.println("HERE ; 1");
            // stop the production and event streams
            productionThread.stop();
            eventThread.stop();
            return 1;
        }
    }
}
