package ch.unisg.factory.core.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "machines")
public class MachineRepository {

    @Id
    @Column(name = "id")
    private String machineId;

    @Column(name = "name")
    private String machineName;

    @Column(name = "status")
    private Boolean machineStatus;

    @Column(name = "inventory_level")
    private int machineInventoryLevel;

    public String getMachineName() {
        return machineName;
    }
}
