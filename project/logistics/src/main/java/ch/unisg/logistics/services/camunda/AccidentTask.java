package ch.unisg.logistics.services.camunda;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Random;

@Service("accident-task")
public class AccidentTask {

    @Autowired
    private ZeebeClient zeebeClient;

    @JobWorker(type = "accident-task", autoComplete = false)
    public void accidentTask(ActivatedJob activatedJob) {
        Random random = new Random();
        double randomDouble = random.nextDouble();

        HashMap variables = new HashMap<>();

        if (randomDouble >0.5) {
            variables.put("accident", true);
        } else {
            variables.put("accident", false);
        }
        zeebeClient.newCompleteCommand(activatedJob.getKey()).variables(variables).send();
    }
}
