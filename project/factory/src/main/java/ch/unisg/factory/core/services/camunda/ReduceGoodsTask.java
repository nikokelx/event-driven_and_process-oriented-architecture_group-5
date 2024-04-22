package ch.unisg.factory.core.services.camunda;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("reduce-goods")
public class ReduceGoodsTask {

    @Autowired
    private ZeebeClient zeebeClient;

    @JobWorker(type = "reduce-goods-task", autoComplete = false)
    public void reduceGoods(
            final JobClient jobClient,
            final ActivatedJob activatedJob,
            @Variable int error
    ) {

        System.out.println("Event: Reduce the amount of goods.");


        HashMap variables = new HashMap();
        variables.put("error", 1);

        zeebeClient.newCompleteCommand(activatedJob.getKey())
                .variables(variables)
                .send();
    }
}
