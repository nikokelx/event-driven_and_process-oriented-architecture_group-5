package ch.unisg.machines.core.ports.in;

public interface ToggleProductionUseCase {
    int toggleProduction(ToggleProductionCommand command);
}
