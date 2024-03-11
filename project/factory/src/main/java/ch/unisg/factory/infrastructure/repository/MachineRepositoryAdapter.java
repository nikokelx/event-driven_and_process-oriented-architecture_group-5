package ch.unisg.factory.infrastructure.repository;

import ch.unisg.factory.core.entities.MachineRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MachineRepositoryAdapter extends JpaRepository<MachineRepository, Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE machines SET status=?2 WHERE id=?1", nativeQuery = true)
    void setMachineStatusById(Integer id, Boolean status);
}
