package ch.unisg.machine01.core.ports.out;

import ch.unisg.machine01.core.entities.Machine;

public interface FillLevelEventPort {
    void publishFillLevel(Machine.MachineFillLevel machineFillLevel);
}
