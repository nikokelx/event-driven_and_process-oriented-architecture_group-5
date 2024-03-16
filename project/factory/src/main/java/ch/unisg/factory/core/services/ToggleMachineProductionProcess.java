package ch.unisg.factory.core.services;

import ch.unisg.factory.core.entities.Machine;
import ch.unisg.factory.core.ports.out.ToggleMachineProductionPort;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("ToggleMachineProduction")
@RequiredArgsConstructor
public class ToggleMachineProductionProcess implements JavaDelegate {

    private final ToggleMachineProductionPort toggleMachineProductionPort;
    private Machine machine = Machine.getMachine();

    @Override
    public void execute(DelegateExecution execution) {
        String response = toggleMachineProductionPort.toggleMachineProduction();
        System.out.println(response);
    }
}
