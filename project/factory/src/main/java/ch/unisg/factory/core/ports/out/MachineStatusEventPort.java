package ch.unisg.factory.core.ports.out;

import ch.unisg.factory.core.entities.Machine;

public interface MachineStatusEventPort {
    void toggleMachineStatus(Machine.MachineStatus machineStatus);
}
