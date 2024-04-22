package ch.unisg.warehouse.core.entities;

import lombok.Getter;
import lombok.Setter;

public class Factory {

    @Getter @Setter
    private InventoryLevel inventoryLevel;

    //Note:--> using the Singleton pattern here to make life easier
    @Getter
    private static final Factory factory = new Factory();

    private Factory() {
        this.inventoryLevel = new InventoryLevel(0);
    }

    public void increaseInventoryLevel(int value) {
        var currentInventoryLevel = inventoryLevel.value();
        inventoryLevel = new InventoryLevel(currentInventoryLevel + value);
        System.out.println("Increased inventory level from { " + currentInventoryLevel + " } to { " + inventoryLevel.value() + " }");
    }

    public void decreaseInventoryLevel(int value) {
        var currentInventoryLevel = inventoryLevel.value();
        var decreasedInventoryLevel = currentInventoryLevel - value;
        inventoryLevel = new InventoryLevel(decreasedInventoryLevel);
        System.out.println("Decreased inventory level from { " + currentInventoryLevel + " } to { " + decreasedInventoryLevel + " }");
    }

    public record InventoryLevel(int value) { }
}













