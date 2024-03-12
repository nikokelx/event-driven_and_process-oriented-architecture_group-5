package ch.unisg.warehouse.infrastructure.repository;

import ch.unisg.warehouse.core.entities.Factory;
import ch.unisg.warehouse.core.ports.out.UpdateFactoryInventoryLevelPort;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@RequiredArgsConstructor
public class FactoryInventoryLevelAdapter implements UpdateFactoryInventoryLevelPort {

    private final FactoryInventoryLevelRepository factoryInventoryLevelRepository;

    @Override
    public void updateFactoryInventoryLevel(Factory.FactoryInventoryLevel factoryInventoryLevel) {
        factoryInventoryLevelRepository.updateFactoryInventoryLevelById(0, factoryInventoryLevel.getValue());

    }
}
