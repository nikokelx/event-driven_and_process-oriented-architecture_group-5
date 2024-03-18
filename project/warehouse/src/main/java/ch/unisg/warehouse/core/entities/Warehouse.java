package ch.unisg.warehouse.core.entities;

import lombok.Getter;

public class Warehouse {

    @Getter
    private StockLevel stockLevel;

    //Note:--> using the Singleton pattern here to make lives easy
    @Getter
    private static final Warehouse warehouse = new Warehouse();

    private Warehouse() {
        this.stockLevel = new StockLevel(0);
    }

    public void increaseStockLevel(int value) {
        var currentStockLevel = stockLevel.value();
        stockLevel = new StockLevel(currentStockLevel + value);
        System.out.println("Increased inventory level from { " + currentStockLevel + " } to { " + stockLevel.value() + " }");
    }

    public void decreaseStockLevel(int value) {
        var currentStockLevel = stockLevel.value();
        var decreasedStockLevel = currentStockLevel - value;
        stockLevel = new StockLevel(decreasedStockLevel);
        System.out.println("Decreased inventory level from { " + currentStockLevel + " } to { " + decreasedStockLevel + " }");
    }

    public record StockLevel(int value) { }
}
