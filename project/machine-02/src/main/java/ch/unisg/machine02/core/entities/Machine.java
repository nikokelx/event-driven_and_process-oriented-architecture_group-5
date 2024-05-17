package ch.unisg.machine02.core.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

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

    @Getter
    private MachineCapacity machineCapacity;

    @Getter
    private MachineProductionStatus machineProductionStatus;

    @Setter @Getter
    private MachineProductionSpeed machineProductionSpeed;

    private static final Machine machine = new Machine(
            new MachineId(0),
            new MachineStatus(Status.INACTIVE),
            new MachineFillLevel(0),
            new MachineCapacity(100),
            new MachineProductionStatus(false),
            new MachineProductionSpeed(0)
    );

    private Machine(
            MachineId machineId,
            MachineStatus machineStatus,
            MachineFillLevel machineFillLevel,
            MachineCapacity machineCapacity,
            MachineProductionStatus machineProductionStatus,
            MachineProductionSpeed machineProductionSpeed
    ) {
        this.machineId = machineId;
        this.machineStatus = machineStatus;
        this.machineFillLevel = machineFillLevel;
        this.machineCapacity = machineCapacity;
        this.machineProductionStatus = machineProductionStatus;
        this.machineProductionSpeed = machineProductionSpeed;
    }

    public static Machine getMachine() {
        return machine;
    }

    public void setMachineFillLevel(int value) {
        this.machineFillLevel = new MachineFillLevel(value);
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

        private int increment;

        public ProductionThread(int sleepInterval, int productionIncrement) {
            interval = sleepInterval;
            increment = productionIncrement;
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

                int machineFillLevel = machine.getMachineFillLevel().getValue();

                if (machineFillLevel < machine.getMachineCapacity().getValue()) {
                    machine.setMachineFillLevel(machineFillLevel + this.increment);
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
        int value;
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
