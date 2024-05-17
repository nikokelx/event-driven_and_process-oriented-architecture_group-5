package ch.unisg.machine02.core.ports.in;

public interface ToggleProductionUseCase {
    int toggleProduction(ToggleProductionCommand command);
}
