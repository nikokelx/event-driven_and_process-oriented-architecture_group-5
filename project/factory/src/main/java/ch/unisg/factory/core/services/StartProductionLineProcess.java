package ch.unisg.factory.core.services;

import ch.unisg.factory.core.entities.Machine;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("StartProductionLine")
public class StartProductionLineProcess implements JavaDelegate {

    private Machine machine = Machine.getMachine();

    @Override
    public void execute(DelegateExecution execution) {

        if (machine.getMachineStatus().getValue()) {

            execution.setVariable("machineStatus", (boolean)true);

        } else {

            execution.setVariable("machineStatus", (boolean)false);

        }


    }
}
