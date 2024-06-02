package ch.unisg.factory.core.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class TransferOfGoodsRequest {

    private final Id id;

    private final GoodsAmount goodsAmount;

    @Setter
    private RequestStatus requestStatus;

    public TransferOfGoodsRequest(GoodsAmount goodsAmount) {
        this.id = new Id(UUID.randomUUID().toString());
        this.goodsAmount = goodsAmount;
        this.requestStatus = new RequestStatus(Status.PENDING);
    }

    public enum Status {
        PENDING, COMPLETED
    }

    public record Id(String value) { }
    public record GoodsAmount(double value) { }
    public record RequestStatus(Status value) { }
}
