package ch.unisg.machine01.core.ports.out.streams;

import ch.unisg.machine01.core.entities.Machine;

public interface TemperatureStreamPort {
    void streamTemperature(Machine.MachineTemperature machineTemperature);
}
