package ch.unisg.machine01.core.ports.in;

public interface ToggleProductionUseCase {
    int toggleProduction(ToggleProductionCommand command);
}
