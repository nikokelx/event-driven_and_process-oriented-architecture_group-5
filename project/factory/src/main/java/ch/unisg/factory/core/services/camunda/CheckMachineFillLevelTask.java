package ch.unisg.factory.core.services.camunda;

import ch.unisg.factory.core.entities.Machine;
import ch.unisg.factory.core.entities.Outbox;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@RequiredArgsConstructor
@Service("check-machine-fill-level")
public class CheckMachineFillLevelTask {

    @Autowired
    private ZeebeClient zeebeClient;

    private Outbox outbox = Outbox.getOutbox();

    private final Machine machine = Machine.getMachine();

    @JobWorker(type = "check-machine-fill-level-task", autoComplete = false)
    public void checkMachineFillLevelTask(
            final JobClient jobClient,
            final ActivatedJob activatedJob
    ) {

        System.out.println("Task: Check Machine Fill Level");

        HashMap variables = new HashMap();

        Machine.MachineFillLevel machineFillLevel = machine.getMachineFillLevel();
        outbox.addMachineFillLevel(machineFillLevel.getValue());

        variables.put("machineFillLevel", machineFillLevel.getValue());

        zeebeClient.newCompleteCommand(activatedJob.getKey())
                .variables(variables)
                .send();
    }
}
