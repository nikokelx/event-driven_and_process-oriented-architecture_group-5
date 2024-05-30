package ch.unisg.factory.core.services.camunda07;

import ch.unisg.factory.core.entities.Machine;
import ch.unisg.factory.core.ports.out.ToggleMachineStatusPort;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("ToggleMachineStatus")
@RequiredArgsConstructor
public class ToggleMachineStatusProcess implements JavaDelegate {

    private Machine machine = Machine.getMachine();

    private final ToggleMachineStatusPort toggleMachineStatusPort;

    @Override
    public void execute(DelegateExecution execution) {

        System.out.println(execution.getVariable("machineStatus"));

        String respone = toggleMachineStatusPort.toggleMachineStatus();

        System.out.println(respone);

    }


}
