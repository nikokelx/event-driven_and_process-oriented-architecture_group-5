package ch.unisg.factory.core.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

public class Machine {

    @Getter
    private MachineId machineId;

    @Getter
    private MachineName machineName;

    @Getter @Setter
    private MachineStatus machineStatus;

    @Getter @Setter
    private MachineFillLevel machineFillLevel;


    private Machine(
            Machine.MachineId machineId,
            Machine.MachineName machineName,
            Machine.MachineStatus machineStatus,
            Machine.MachineFillLevel machineInventoryLevel
    ) {
        this.machineId = machineId;
        this.machineName = machineName;
        this.machineStatus = machineStatus;
        this.machineFillLevel = machineFillLevel;
    }

    public static Machine addMachine(
            Machine.MachineId machineId,
            Machine.MachineName machineName,
            Machine.MachineStatus machineStatus,
            Machine.MachineFillLevel machineFillLevel
    ) {
        return new Machine(machineId, machineName, machineStatus, machineFillLevel);
    }

    @Value
    public static class MachineId {
        String value;
    }

    @Value
    public static class MachineName {
        String value;
    }

    @Value
    public static class MachineStatus {
        Boolean value;
    }

    @Value
    public static class MachineFillLevel {
        int value;
    }
}
