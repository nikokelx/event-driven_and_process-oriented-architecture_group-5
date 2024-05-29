package ch.unisg.machine02.core.ports.out;

import ch.unisg.machine02.core.entities.Machine;
import ch.unisg.machine02.core.entities.MachineData;

public interface FillLevelEventPort {
    void publishFillLevel(Machine.MachineFillLevel machineFillLevel);
    void publishMachineData(MachineData machineData);
}
