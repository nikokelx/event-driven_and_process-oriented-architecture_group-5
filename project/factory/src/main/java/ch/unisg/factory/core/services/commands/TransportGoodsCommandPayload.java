package ch.unisg.factory.core.services.commands;

public class TransportGoodsCommandPayload {

    private String refId;
    private double amount;

    public String getRefId() {
        return refId;
    }
    public TransportGoodsCommandPayload setRefId(String refId) {
        this.refId = refId;
        return this;
    }
    public double getAmount() {
        return amount;
    }
    public TransportGoodsCommandPayload setAmount(double amount) {
        this.amount = amount;
        return this;
    }
}
