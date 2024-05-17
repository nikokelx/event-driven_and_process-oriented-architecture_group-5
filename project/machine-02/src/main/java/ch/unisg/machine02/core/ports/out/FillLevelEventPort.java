package ch.unisg.machine02.core.ports.out;

import ch.unisg.machine02.core.entities.Machine;

public interface FillLevelEventPort {
    void publishFillLevel(Machine.MachineFillLevel machineFillLevel);
}
