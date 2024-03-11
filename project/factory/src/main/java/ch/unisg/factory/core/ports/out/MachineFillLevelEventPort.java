package ch.unisg.factory.core.ports.out;

import ch.unisg.factory.core.entities.Machine;

public interface MachineFillLevelEventPort {
    void updateMachineFillLevelEvent(Machine.MachineFillLevel machineFillLevel);
}
