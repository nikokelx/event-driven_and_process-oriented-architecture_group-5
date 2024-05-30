package ch.unisg.eventprocessor.model;

public class MachineTemperature {

    private String timestamp;
    private double temperature;

    public String getTimestamp() {
        return timestamp;
    }

    public double getMachineTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return "{" + " timestamp:'" + getTimestamp() + "'" + ", temperature:'" + getMachineTemperature() + "'" + "}";
    }
}
