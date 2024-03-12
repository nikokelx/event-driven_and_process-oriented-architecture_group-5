package ch.unisg.factory.infrastructure.repository;

import ch.unisg.factory.core.entities.Factory;
import ch.unisg.factory.core.ports.out.SaveFactoryInventoryLevelPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@RequiredArgsConstructor
public class FactoryInventoryLevelAdapter implements SaveFactoryInventoryLevelPort {

    private final FactoryInventoryLevelRepository factoryInventoryLevelRepository;

    @Override
    public void saveFactoryInventoryLevel(Factory.FactoryInventoryLevel factoryInventoryLevel) {
        factoryInventoryLevelRepository.saveFactoryInventoryLevelById(0, factoryInventoryLevel.getValue());
    }
}
