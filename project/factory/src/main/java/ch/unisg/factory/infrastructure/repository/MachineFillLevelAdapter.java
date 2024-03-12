package ch.unisg.factory.infrastructure.repository;

import ch.unisg.factory.core.entities.Machine;
import ch.unisg.factory.core.ports.out.MachineFillLevelEventPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@RequiredArgsConstructor
public class MachineFillLevelAdapter implements MachineFillLevelEventPort {

    private final MachineFillLevelRepository machineFillLevelRepository;

    @Override
    public void updateMachineFillLevelEvent(Machine.MachineFillLevel machineFillLevel){

        machineFillLevelRepository.updateMachineFillLevelById(0, machineFillLevel.getValue());

    }
}
