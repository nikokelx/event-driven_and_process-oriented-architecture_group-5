package ch.unisg.machine02.core.ports.out;

import ch.unisg.machine02.core.entities.Machine;

public interface MachineStatusEventPort {
    void toggleMachineStatus(Machine.MachineStatus machineStatus);
}
