package ch.unisg.machines.core.ports.in;

import ch.unisg.machines.core.entities.Machine;

public interface ToggleMachineUseCase {
    Machine.MachineStatus toggleMachine(ToggleMachineCommand command);
}
