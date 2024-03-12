package ch.unisg.warehouse.infrastructure.repository;

import ch.unisg.warehouse.core.entities.FactoryRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FactoryInventoryLevelRepository extends JpaRepository<FactoryRepo, Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE factory SET inventory_level=inventory_level + ?2 WHERE id=?1", nativeQuery = true)
    void updateFactoryInventoryLevelById(Integer id, Integer inventoryLevel);

}
