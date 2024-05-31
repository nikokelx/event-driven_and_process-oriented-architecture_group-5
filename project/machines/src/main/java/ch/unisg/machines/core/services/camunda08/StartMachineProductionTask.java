package ch.unisg.machines.core.services.camunda08;

import ch.unisg.machines.core.entities.Machine;
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

        // create a new production thread
        Machine.ProductionThread productionThread = new Machine.ProductionThread(2000);

        // get machine production status
        Machine.MachineProductionStatus machineProductionStatus = machine.getMachineProductionStatus();

        // toggle the machine production
        machineProductionStatus.toggle();

        // if the production is off
        if (machineProductionStatus.getMachineProductionStatus()) {
            // start the production
            productionThread.start();
        } else {
            // stop the production
            productionThread.stop();
        }

        // complete the camunda 8 service task
        zeebeClient.newCompleteCommand(activatedJob.getKey())
                .send();

    }
}
