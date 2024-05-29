package ch.unisg.machine01.core.ports.out.streams;

import ch.unisg.machine01.core.entities.Machine;

public interface FillLevelStreamPort {
    void streamFillLevel(Machine.MachineFillLevel machineFillLevel);
}
