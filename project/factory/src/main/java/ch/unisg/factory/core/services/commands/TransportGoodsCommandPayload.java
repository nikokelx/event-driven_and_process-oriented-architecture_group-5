package ch.unisg.factory.core.services.commands;

public class TransportGoodsCommandPayload {

    private String refId;
    private int amount;

    public String getRefId() {
        return refId;
    }
    public TransportGoodsCommandPayload setRefId(String refId) {
        this.refId = refId;
        return this;
    }
    public int getAmount() {
        return amount;
    }
    public TransportGoodsCommandPayload setAmount(int amount) {
        this.amount = amount;
        return this;
    }
}
