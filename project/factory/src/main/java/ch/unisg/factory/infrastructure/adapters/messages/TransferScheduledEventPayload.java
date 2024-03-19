package ch.unisg.factory.infrastructure.adapters.messages;

public class TransferScheduledEventPayload {
    private String refId;

    public String getRefId() {
        return refId;
    }

    public TransferScheduledEventPayload setRefId(String refId) {
        this.refId = refId;
        return this;
    }
}
