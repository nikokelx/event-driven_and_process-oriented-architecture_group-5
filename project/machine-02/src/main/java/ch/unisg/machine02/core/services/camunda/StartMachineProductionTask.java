package ch.unisg.machine02.core.services.camunda;

import ch.unisg.machine02.core.entities.Machine;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("start-machine-production")
public class StartMachineProductionTask {

    @Autowired
    private ZeebeClient zeebeClient;

    private Machine machine = Machine.getMachine();

    @JobWorker(type = "start-machine-production-task", autoComplete = false)
    public void startMachineProduction(final JobClient jobClient, final ActivatedJob activatedJob) {

        System.out.println("Event: Start Machine Production");

        // Modified for part 2
        // Machine.ProductionThread productionThread = new Machine.ProductionThread(2000, machine.getMachineProductionSpeed().getValue());
        Machine.ProductionThread productionThread = new Machine.ProductionThread(2000);
        Machine.MachineProductionStatus machineProductionStatus = machine.getMachineProductionStatus();

        machineProductionStatus.toggle();

        if (machineProductionStatus.getMachineProductionStatus()) {
            productionThread.start();
        } else {
            productionThread.stop();
        }

        zeebeClient.newCompleteCommand(activatedJob.getKey())
                .send();
    }
}
