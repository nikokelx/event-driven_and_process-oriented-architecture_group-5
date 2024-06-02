package ch.unisg.factory.core.entities;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class Factory {

    // inventory level of the factory
    @Getter
    private InventoryLevel inventoryLevel;

    // list of goods transfer
    @Getter
    private final TransferOfGoodsRequestList transferOfGoodsRequestList;

    //Note:--> using the Singleton pattern here to make lives easy
    @Getter
    private static final Factory factory = new Factory();

    private Factory() {
        this.inventoryLevel = new InventoryLevel(0);
        this.transferOfGoodsRequestList = new TransferOfGoodsRequestList(new LinkedList<>());
    }

    public void increaseInventoryLevel(double value) {
        var currentInventoryLevel = inventoryLevel.value();
        inventoryLevel = new InventoryLevel(currentInventoryLevel + value);
        System.out.println("Increased inventory level from { " + currentInventoryLevel + " } to { " + inventoryLevel.value() + " }");
    }

    public void decreaseInventoryLevel(double value) {
        var currentInventoryLevel = inventoryLevel.value();
        var decreasedInventoryLevel = currentInventoryLevel - value;
        inventoryLevel = new InventoryLevel(decreasedInventoryLevel);
        System.out.println("Decreased inventory level from { " + currentInventoryLevel + " } to { " + decreasedInventoryLevel + " }");
    }

    public void addNewTransferRequestToList(TransferOfGoodsRequest request) {
        transferOfGoodsRequestList.value.add(request);
        System.out.println("Added new Transfer Request to list.");
    }

    public TransferOfGoodsRequest getTransferOfGoodsRequestById(String requestId) {
        for (TransferOfGoodsRequest request : transferOfGoodsRequestList.value) {
            if (request.getId().value().equalsIgnoreCase(requestId)) {
                return request;
            }
        }
        return null;
    }

    public record InventoryLevel(double value) { }

    public record TransferOfGoodsRequestList(List<TransferOfGoodsRequest> value) { }
}