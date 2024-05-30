package ch.unisg.machines.core.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Machine {

    public enum Status {
        ACTIVE, INACTIVE
    }

    // Identifier for the machine
    @Getter @Setter
    private MachineId machineId;

    // Machine Status (On / Off)
    @Setter @Getter
    private MachineStatus machineStatus;

    // Machine Fill Level (0 -> Machine Capacity)
    @Setter @Getter
    private MachineFillLevel machineFillLevel;

    // Machine Last Production Unit
    @Setter @Getter
    private MachineLastIncrease machineLastIncrease;

    // Machine overall Capacity
    @Getter
    private MachineCapacity machineCapacity;

    // Machine Temperature
    @Setter @Getter
    private MachineTemperature machineTemperature;

    // Machine Production Status (On / Off)
    @Getter
    private MachineProductionStatus machineProductionStatus;

    // Machine Production speed per second
    @Setter @Getter
    private MachineProductionSpeed machineProductionSpeed;

    // Create a new singleton machine
    private static final Machine machine = new Machine(
            // retrieve machine id from the environment (docker)
            new MachineId(System.getenv("MACHINE")),
            new MachineStatus(Status.INACTIVE),
            new MachineFillLevel(0),
            new MachineLastIncrease(0),
            new MachineCapacity(100),
            new MachineTemperature(Double.valueOf(System.getenv("TEMPERATURE"))),
            new MachineProductionStatus(false),
            new MachineProductionSpeed(2)
    );

    private Machine(
            MachineId machineId,
            MachineStatus machineStatus,
            MachineFillLevel machineFillLevel,
            MachineLastIncrease machineLastIncrease,
            MachineCapacity machineCapacity,
            MachineTemperature machineTemperature,
            MachineProductionStatus machineProductionStatus,
            MachineProductionSpeed machineProductionSpeed
    ) {
        this.machineId = machineId;
        this.machineStatus = machineStatus;
        this.machineFillLevel = machineFillLevel;
        this.machineLastIncrease = machineLastIncrease;
        this.machineCapacity = machineCapacity;
        this.machineTemperature = machineTemperature;
        this.machineProductionStatus = machineProductionStatus;
        this.machineProductionSpeed = machineProductionSpeed;
    }

    public static Machine getMachine() {
        return machine;
    }

    public boolean getMachineStatus() {
        return "ACTIVE".equals(this.machineStatus.getValue().toString());
    }

    public MachineStatus toggleStatus() {

        if (getMachineStatus()) {
            this.machineStatus = new MachineStatus(Status.INACTIVE);
        } else {
            this.machineStatus = new MachineStatus(Status.ACTIVE);
        }

        return this.machineStatus;
    }

    // Production logic
    public static class ProductionThread implements Runnable {
        private Thread worker;
        private final AtomicBoolean running = new AtomicBoolean(false);
        private int interval;

        private double increment;

        public ProductionThread(int sleepInterval) {
            this.interval = 2000;
            this.increment = machine.getMachineProductionSpeed().getValue();
        }

        public void start() {
            worker = new Thread(this);
            worker.start();
        }

        public void stop() {
            running.set(false);
        }

        public void run() {

            Machine machine = getMachine();

            running.set(true);

            Random random = new Random();

            while (running.get()) {

                // Wait for the next production cycle
                try {
                    Thread.sleep(this.interval);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                // retrieve the machine fill level
                double machineFillLevel = machine.getMachineFillLevel().getValue();

                // if machine fill level is smaller than machine capacity
                if (machineFillLevel < machine.getMachineCapacity().getValue()) {

                    // retrieve a random value between -0.5 and 0.5
                    double rangeSelector = random.nextDouble(-0.5, 0.5);

                    // produce new units
                    double production = this.increment + rangeSelector;

                    double result = production;

                    // increase variables
                    machine.setMachineFillLevel(new MachineFillLevel(machineFillLevel + result));
                    machine.setMachineLastIncrease(new MachineLastIncrease(result));

                } else {

                    // stop the production line automatically
                    this.stop();

                }
            }
        }
    }

    @Value
    public static class MachineId {
        String value;
    }

    @Value
    public static class MachineStatus {
        Status value;
    }

    @Value
    public static class MachineFillLevel {
        double value;
    }

    @Value
    public static class MachineLastIncrease {
        double value;
    }

    @Value
    public static class MachineTemperature {
        double value;
    }

    @Value
    public static class MachineCapacity {
        int value;
    }

    @Value
    public static class MachineProductionSpeed {
        int value;
    }

    public static class MachineProductionStatus {
        private Boolean value;

        public MachineProductionStatus(Boolean value) {
            this.value = value;
        }

        public void toggle() {
            this.value = !this.value;
        }

        public Boolean getValue() { return value; }

        public Boolean getMachineProductionStatus() {
            return value;
        }
    }

}
