package ch.unisg.machine02.core.ports.in;

import ch.unisg.machine02.core.entities.Machine;

public interface ToggleMachineUseCase {
    Machine.MachineStatus toggleMachine(ToggleMachineCommand command);
}
