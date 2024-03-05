package ch.unisg.factory.infrastructure.repository;

import ch.unisg.factory.core.entities.MachineRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepositoryAdapter extends JpaRepository<MachineRepository, String> {
}
