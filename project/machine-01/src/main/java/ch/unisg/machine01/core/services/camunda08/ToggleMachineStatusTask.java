package ch.unisg.machine01.core.services.camunda08;

import ch.unisg.machine01.core.entities.Machine;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("toggle-machine-status")
public class ToggleMachineStatusTask {

    @Autowired
    private ZeebeClient zeebeClient;

    private Machine machine = Machine.getMachine();

    @JobWorker(type = "toggle-machine-status-task")
    public void toggleMachineStatus(final JobClient jobClient, final ActivatedJob activatedJob) {

        System.out.println("Event: Toggle Machine Status");

        // Toggle the machine
        machine.toggleStatus();

        // Update the machine status variable
        HashMap variables = new HashMap();
        variables.put("machineStatus", machine.getMachineStatus());

        zeebeClient.newCompleteCommand(activatedJob.getKey())
                .variables(variables)
                .send();

    }

}
