package ch.unisg.machines.core.ports.out;

import ch.unisg.machines.core.entities.Machine;

public interface FillLevelEventPort {
    void publishFillLevel(Machine.MachineFillLevel machineFillLevel);
}
