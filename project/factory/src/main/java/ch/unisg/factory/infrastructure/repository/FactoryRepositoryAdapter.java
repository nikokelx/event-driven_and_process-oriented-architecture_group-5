package ch.unisg.factory.infrastructure.repository;

import ch.unisg.factory.core.entities.FactoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactoryRepositoryAdapter extends JpaRepository<FactoryRepository, String> {
}
