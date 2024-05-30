package ch.unisg.machines.archive;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MachineData {
    private MachineId machineId;
    private MachineProductionPerSecond machineProductionPerSecond;
    private MachineFillLevel machineFillLevel;

    public record MachineId(String value) { }

    public record MachineProductionPerSecond(double value) { }
    public record MachineFillLevel(double value) { }

}
