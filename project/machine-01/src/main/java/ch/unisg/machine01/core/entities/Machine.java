package ch.unisg.machine01.core.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

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

    private static final Machine machine = new Machine(
            new MachineId(0),
            new MachineStatus(Status.INACTIVE),
            new MachineFillLevel(0)
    );

    private Machine(
            MachineId machineId,
            MachineStatus machineStatus,
            MachineFillLevel machineFillLevel
    ) {
        this.machineId = machineId;
        this.machineStatus = machineStatus;
        this.machineFillLevel = machineFillLevel;
    }

    public static Machine getMachine() {
        return machine;
    }

    public MachineStatus toggleStatus() {

        if (machine.getMachineStatus().getValue().toString().equals("ACTIVE")) {
            this.machineStatus = new MachineStatus(Status.INACTIVE);
        } else {
            this.machineStatus = new MachineStatus(Status.ACTIVE);
        }

        return this.machineStatus;
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
}
