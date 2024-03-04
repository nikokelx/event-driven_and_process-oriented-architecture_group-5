package ch.unisg.factory.infrastructure.repository;

import ch.unisg.factory.core.entities.MachineRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MachineDBRepository extends JpaRepository<MachineRepository, String> {
}
