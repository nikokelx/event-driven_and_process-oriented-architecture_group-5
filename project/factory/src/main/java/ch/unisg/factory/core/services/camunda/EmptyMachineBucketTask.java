package ch.unisg.factory.core.services.camunda;

import ch.unisg.factory.core.entities.Factory;
import ch.unisg.factory.core.entities.Machine;
import ch.unisg.factory.core.ports.out.CollectMachineFillLevelPort;
import com.google.errorprone.annotations.Var;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("empty-machine-bucket")
public class EmptyMachineBucketTask {

    @Autowired
    private ZeebeClient zeebeClient;

    private final CollectMachineFillLevelPort collectMachineFillLevelPort;

    private Factory factory = Factory.getFactory();

    @JobWorker(type = "empty-machine-bucket-task", autoComplete = false)
    public void emptyMachineBucketTask(
            final JobClient jobClient,
            final ActivatedJob activatedJob,
            @Variable int error,
            @Variable int amountGoods
    ) {

        if (error == 0) {
            zeebeClient.newThrowErrorCommand(activatedJob.getKey()).errorCode("ErrorExample").send();
        } else {
            int machineFillLevel = collectMachineFillLevelPort.collectMachineFillLevel();
            factory.increaseInventoryLevel(machineFillLevel - amountGoods);

            zeebeClient.newCompleteCommand(activatedJob.getKey())
                    .send();
        }
    }
}
