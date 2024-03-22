package ch.unisg.warehouse.core.messages;

public class ConfirmTransferCommandPayload {

    private String refId;
    private int amount;

    public String getRefId() {
        return refId;
    }
    public ConfirmTransferCommandPayload setRefId(String refId) {
        this.refId = refId;
        return this;
    }
    public int getAmount() {
        return amount;
    }
    public ConfirmTransferCommandPayload setAmount(int amount) {
        this.amount = amount;
        return this;
    }
}
