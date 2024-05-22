package ch.unisg.machine01.core.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Machine {

    public enum Status {
        ACTIVE, INACTIVE
    }

    @Getter @Setter
    private MachineId machineId;

    @Setter @Getter
    private MachineStatus machineStatus;

    @Setter @Getter
    private MachineFillLevel machineFillLevel;

    @Setter @Getter
    private MachineLastIncrease machineLastIncrease;

    @Getter
    private MachineCapacity machineCapacity;

    @Setter @Getter
    private MachineTemperature machineTemperature;

    @Getter
    private MachineProductionStatus machineProductionStatus;

    @Setter @Getter
    private MachineProductionSpeed machineProductionSpeed;

    private static final Machine machine = new Machine(
            new MachineId(System.getenv("MACHINE")),
            new MachineStatus(Status.INACTIVE),
            new MachineFillLevel(0),
            new MachineLastIncrease(0),
            new MachineCapacity(100),
            new MachineTemperature(0.0),
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

                try {
                    Thread.sleep(this.interval);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                double machineFillLevel = machine.getMachineFillLevel().getValue();

                if (machineFillLevel < machine.getMachineCapacity().getValue()) {

                    double rangeSelector = random.nextDouble(-0.5, 0.5);

                    double production = this.increment + rangeSelector;

                    double result = production;

                    machine.setMachineFillLevel(new MachineFillLevel(machineFillLevel + result));
                    machine.setMachineLastIncrease(new MachineLastIncrease(result));

                } else {

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
