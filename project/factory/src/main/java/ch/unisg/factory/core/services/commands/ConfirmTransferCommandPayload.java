package ch.unisg.factory.core.services.commands;

public class ConfirmTransferCommandPayload {

    private String refId;
    private double amount;

    public String getRefId() {
        return refId;
    }
    public ConfirmTransferCommandPayload setRefId(String refId) {
        this.refId = refId;
        return this;
    }
    public double getAmount() {
        return amount;
    }
    public ConfirmTransferCommandPayload setAmount(double amount) {
        this.amount = amount;
        return this;
    }
}
