package ch.unisg.machine01.core.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

public class Machine {

    public enum Status {
        ACTIVE, INACTIVE
    }

    @Getter
    private MachineStatus machineStatus;

    @Setter @Getter
    private  MachineFillLevel machineFillLevel;

    public Machine() {
        this.machineStatus = new MachineStatus(Status.ACTIVE);
        this.machineFillLevel = new MachineFillLevel(0);
    }

    public boolean toggleStatus() {
        System.out.println(getMachineStatus().getValue());
        this.machineStatus = new MachineStatus(Status.INACTIVE);
        return false;
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
