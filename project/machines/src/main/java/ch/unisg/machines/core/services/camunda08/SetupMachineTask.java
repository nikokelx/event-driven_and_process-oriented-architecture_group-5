package ch.unisg.machines.core.services.camunda08;


import ch.unisg.machines.core.entities.Machine;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("setup-machine")
public class SetupMachineTask {

    @Autowired
    private ZeebeClient zeebeClient;

    private Machine machine = Machine.getMachine();

    @JobWorker(type = "setup-machine-task", autoComplete = false)
    public void setupMachine(final JobClient jobClient, final ActivatedJob activatedJob) {

        System.out.println("Event: Setting up machine");

        // save machine status to variable
        HashMap variables = new HashMap();
        variables.put("machineStatus", machine.getMachineStatus());

        // complete camunda 8 service task
        zeebeClient.newCompleteCommand(activatedJob.getKey()).variables(variables).send();


    }

}
