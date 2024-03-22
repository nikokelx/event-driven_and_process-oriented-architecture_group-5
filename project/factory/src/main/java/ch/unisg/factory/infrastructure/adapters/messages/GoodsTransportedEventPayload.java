package ch.unisg.factory.infrastructure.adapters.messages;

public class GoodsTransportedEventPayload {

    private String refId;

    public String getRefId() {
        return refId;
    }

    public GoodsTransportedEventPayload setRefId(String refId) {
        this.refId = refId;
        return this;
    }
}
