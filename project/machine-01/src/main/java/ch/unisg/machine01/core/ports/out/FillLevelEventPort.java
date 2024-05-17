package ch.unisg.machine01.core.ports.out;

import ch.unisg.machine01.core.entities.Machine;
import ch.unisg.machine01.core.entities.MachineData;

public interface FillLevelEventPort {
    void publishFillLevel(Machine.MachineFillLevel machineFillLevel);
    void publishMachineData(MachineData machineData);
}
