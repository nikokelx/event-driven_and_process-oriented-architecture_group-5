package ch.unisg.machine01.core.entities;

import lombok.Getter;
import lombok.Value;

public class Machine {

    public enum Status {
        ACTIVE, INACTIVE
    }

    @Getter
    private MachineStatus machineStatus;

    public Machine() {
        this.machineStatus = new MachineStatus(Status.ACTIVE);
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
}
