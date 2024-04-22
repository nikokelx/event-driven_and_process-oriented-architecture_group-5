package ch.unisg.factory.core.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Outbox {

    private List<Integer> machineFillLevels = new ArrayList<>();

    private Outbox() {
        this.machineFillLevels.add(0);
    }

    private static final Outbox outbox = new Outbox();

    public static Outbox getOutbox() {
        return outbox;  // Create and return a new instance
    }

    public void addMachineFillLevel(int machineFillLevel) {
        machineFillLevels.add(machineFillLevel);
    };

    public int getLastMachineFillLevel() {
        if (machineFillLevels.isEmpty()) {
            return 0;
        }
        return machineFillLevels.get(machineFillLevels.size() -1 );
    }

}
