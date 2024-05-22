package ch.unisg.eventprocessor.model;

public class MachineProduction {
    private String timestamp;
    private double production;

    public String getTimestamp() {
        return timestamp;
    }

    public double getMachineProduction() {
        return production;
    }

    @Override
    public String toString() {
        return "{" + " timestamp:'" + getTimestamp() + "'" + ", production:'" + getMachineProduction() + "'" + "}";
    }
}
