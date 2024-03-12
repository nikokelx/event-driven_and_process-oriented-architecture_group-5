package ch.unisg.factory.infrastructure.repository;

import ch.unisg.factory.core.entities.FactoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FactoryInventoryLevelRepository extends JpaRepository<FactoryRepository, String> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE factory SET inventory_level=inventory_level + ?2 WHERE id=?1", nativeQuery = true)
    void saveFactoryInventoryLevelById(Integer id, Integer inventoryLevel);
}
