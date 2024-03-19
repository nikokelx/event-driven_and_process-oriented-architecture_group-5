package ch.unisg.factory.core.services.commands;

public class ScheduleTransferCommandPayload {

    private String refId;
    private int amount;

    public String getRefId() {
        return refId;
    }
    public ScheduleTransferCommandPayload setRefId(String refId) {
        this.refId = refId;
        return this;
    }
    public int getAmount() {
        return amount;
    }
    public ScheduleTransferCommandPayload setAmount(int amount) {
        this.amount = amount;
        return this;
    }
}
