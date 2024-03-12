package ch.unisg.warehouse.core.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "factory")
public class FactoryRepo {

    @Id
    @Column(name = "id")
    private Integer factoryId;

    @Column(name = "name")
    private String factoryName;

    @Column(name = "inventory_level")
    private String inventoryLevel;

}
