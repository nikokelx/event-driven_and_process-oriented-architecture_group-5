package ch.unisg.eventprocessor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class Machine {
    private MachineProduction machineProduction;

    private MachineTemperature machineTemperature;

    public Machine(MachineProduction machineProduction, MachineTemperature machineTemperature) {
        this.machineProduction =machineProduction;
        this.machineTemperature = machineTemperature;
    }

    // Getters and Setters

    public double getProduction() {
        return machineProduction.getMachineProduction();
    }

    public double getTemperature() {
        return machineTemperature.getMachineTemperature();
    }

    @Override
    public String toString() {
        return "{" + " machine_production:'" + getProduction() + "'" + ", machine_temperature:'" + getTemperature() + "'" + "}";
    }

}
