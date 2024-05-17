package ch.unisg.machine01.core.entities;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MachineData {
    private MachineId machineId;
    private MachineProductionPerSecond machineProductionPerSecond;
    private MachineFillLevel machineFillLevel;

    public record MachineId(int value) { }

    public record MachineProductionPerSecond(double value) { }
    public record MachineFillLevel(int value) { }

}
