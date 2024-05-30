package ch.unisg.machines.core.ports.out.streams;

import ch.unisg.machines.core.entities.Machine;

public interface TemperatureStreamPort {
    void streamTemperature(Machine.MachineTemperature machineTemperature);
}
