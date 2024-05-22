package ch.unisg.eventprocessor.model;

public class MachineFillLevel {

    private String timestamp;
    private double filllevel;

    public String getTimestamp() {
        return timestamp;
    }

    public double getMachineFillLevel() {
        return filllevel;
    }

    @Override
    public String toString() {
        return "{" + " timestamp:'" + getTimestamp() + "'" + ", filllevel:'" + getMachineFillLevel() + "'" + "}";
    }

}
