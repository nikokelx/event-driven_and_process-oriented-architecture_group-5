package ch.unisg.machine01.core.ports.in;

import ch.unisg.machine01.core.entities.Machine;

public interface ToggleMachineUseCase {
    Machine.MachineStatus toggleMachine(ToggleMachineCommand command);
}
