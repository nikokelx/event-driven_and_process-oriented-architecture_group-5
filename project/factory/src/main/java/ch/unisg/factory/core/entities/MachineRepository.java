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
    private Integer machineId;

    @Column(name = "name")
    private String machineName;

    @Column(name = "status")
    private Boolean machineStatus;

    @Column(name = "fill_level")
    private int machineFillLevel;

    @Column(name = "capacity")
    private int capacity;

    public String getMachineName() {
        return machineName;
    }
}
