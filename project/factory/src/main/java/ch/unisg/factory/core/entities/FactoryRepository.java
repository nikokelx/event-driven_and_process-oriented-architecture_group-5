package ch.unisg.factory.core.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "factory")
public class FactoryRepository {

    @Id
    @Column(name = "id")
    private String factoryId;

    @Column(name = "inventory_level")
    private String inventory_level;

    public String getInventoryLevel() {
        return inventory_level;
    }
}
