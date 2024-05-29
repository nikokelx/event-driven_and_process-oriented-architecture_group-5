package ch.unisg.machine01.core.ports.out.streams;

import java.util.HashMap;

public interface MachineDataStreamPort {
    public void streamMachineData(HashMap event);
}
