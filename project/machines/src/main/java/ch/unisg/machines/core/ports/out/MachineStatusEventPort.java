package ch.unisg.machines.core.ports.out;

import ch.unisg.machines.core.entities.Machine;

public interface MachineStatusEventPort {
    void toggleMachineStatus(Machine.MachineStatus machineStatus);
}
