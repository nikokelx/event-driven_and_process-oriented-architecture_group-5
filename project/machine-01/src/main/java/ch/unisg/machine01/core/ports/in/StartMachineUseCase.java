package ch.unisg.machine01.core.ports.in;

import ch.unisg.machine01.core.entities.Machine;

public interface StartMachineUseCase {
    Machine.MachineStatus startMachine(StartMachineCommand command);
}
