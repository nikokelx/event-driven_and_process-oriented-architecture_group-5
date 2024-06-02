package ch.unisg.factory.core.services.commands;

public class ScheduleTransferCommandPayload {

    private String refId;
    private double amount;

    public String getRefId() {
        return refId;
    }
    public ScheduleTransferCommandPayload setRefId(String refId) {
        this.refId = refId;
        return this;
    }
    public double getAmount() {
        return amount;
    }
    public ScheduleTransferCommandPayload setAmount(double amount) {
        this.amount = amount;
        return this;
    }
}
