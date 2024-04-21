package ch.unisg.machine01.core.services.camunda;


import ch.unisg.machine01.core.entities.Machine;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("setup-machine")
public class SetupMachineProcess {

    @Autowired
    private ZeebeClient zeebeClient;

    private Machine machine = Machine.getMachine();

    @JobWorker(type = "setup-machine")
    public void setupMachine(final JobClient jobClient, final ActivatedJob activatedJob) {
        System.out.println("Event: Setting up machine");

        System.out.println("HELLO");
        HashMap variables = new HashMap();
        variables.put("machineStatus", String.valueOf(machine.getMachineStatus()));

        zeebeClient.newCompleteCommand(activatedJob.getKey()).variables(variables).send();
    }

}
