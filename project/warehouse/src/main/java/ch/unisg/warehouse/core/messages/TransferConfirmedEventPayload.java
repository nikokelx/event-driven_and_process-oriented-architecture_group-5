package ch.unisg.warehouse.core.messages;

public class TransferConfirmedEventPayload {

    private String refId;

    public String getRefId() {
        return refId;
    }

    public TransferConfirmedEventPayload setRefId(String refId) {
        this.refId = refId;
        return this;
    }
}
