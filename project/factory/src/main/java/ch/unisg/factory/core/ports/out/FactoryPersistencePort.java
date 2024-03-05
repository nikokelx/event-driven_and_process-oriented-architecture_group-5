package ch.unisg.factory.core.ports.out;

import ch.unisg.factory.core.entities.Machine;

public interface FactoryPersistencePort {
    int saveMachineFillLevel(Machine.MachineFillLevel machineFillLevel);
}
