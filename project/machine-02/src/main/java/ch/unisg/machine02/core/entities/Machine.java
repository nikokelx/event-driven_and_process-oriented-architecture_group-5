package ch.unisg.machine02.core.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Machine {

    public enum Status {
        ACTIVE, INACTIVE
    }

    @Getter
    final MachineId machineId;

    @Setter @Getter
    private MachineStatus machineStatus;

    @Setter @Getter
    private  MachineFillLevel machineFillLevel;

    @Setter @Getter
    private MachineLastIncrease machineLastIncrease;

    @Getter
    private MachineCapacity machineCapacity;

    @Getter
    private MachineProductionStatus machineProductionStatus;

    @Setter @Getter
    private MachineProductionSpeed machineProductionSpeed;

    private static final Machine machine = new Machine(
            new MachineId(2),
            new MachineStatus(Status.INACTIVE),
            new MachineFillLevel(0),
            new MachineLastIncrease(0),
            new MachineCapacity(100),
            new MachineProductionStatus(false),
            new MachineProductionSpeed(0)
    );

    private Machine(
            MachineId machineId,
            MachineStatus machineStatus,
            MachineFillLevel machineFillLevel,
            MachineLastIncrease machineLastIncrease,
            MachineCapacity machineCapacity,
            MachineProductionStatus machineProductionStatus,
            MachineProductionSpeed machineProductionSpeed
    ) {
        this.machineId = machineId;
        this.machineStatus = machineStatus;
        this.machineFillLevel = machineFillLevel;
        this.machineLastIncrease = machineLastIncrease;
        this.machineCapacity = machineCapacity;
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

        private double CalculateRandomIncrease() {
            Random random = new Random();
            double rangeSelector = random.nextDouble();

            double PROBABILITY_HIGH_RANGE = 0.9;
            double LOW_RANGE_MIN = 0.5;
            double LOW_RANGE_MAX = 0.8;
            double HIGH_RANGE_MIN = 0.8;
            double HIGH_RANGE_MAX = 1.3;

            // If the generated number is within the high probability range
            if (rangeSelector < PROBABILITY_HIGH_RANGE) {
                // Return a random value between 0.8 and 1.3
                return HIGH_RANGE_MIN + (HIGH_RANGE_MAX - HIGH_RANGE_MIN) * random.nextDouble();
            } else {
                // Return a random value between 0.5 and 0.8
                return LOW_RANGE_MIN + (LOW_RANGE_MAX - LOW_RANGE_MIN) * random.nextDouble();
            }
        }

        // Modified for part 2
//        public ProductionThread(int sleepInterval, int productionIncrement) {
//            interval = sleepInterval;
//            increment = productionIncrement;
//        }

        public ProductionThread(int sleepInterval) {
            interval = sleepInterval;
            increment = CalculateRandomIncrease();
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

            while (running.get()) {

                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                double machineFillLevel = machine.getMachineFillLevel().getValue();

                if (machineFillLevel < machine.getMachineCapacity().getValue()) {
                    machine.setMachineFillLevel(new MachineFillLevel(machineFillLevel + this.increment));
                    machine.setMachineLastIncrease(new MachineLastIncrease(this.increment));
                } else {
                    this.stop();
                }

                System.out.println(machineFillLevel);
            }
        }

    }

    @Value
    public static class MachineId {
        int value;
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

        public Boolean getMachineProductionStatus() {
            return value;
        }
    }

}
