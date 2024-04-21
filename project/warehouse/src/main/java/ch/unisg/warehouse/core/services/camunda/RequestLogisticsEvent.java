package ch.unisg.warehouse.core.services.camunda;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("request-logistics-event")
public class RequestLogisticsEvent {

    @Autowired
    private ZeebeClient zeebeClient;

    @JobWorker(type = "requests-logistics-event", autoComplete = false)
    public void requestLogisticsEvent(ActivatedJob activatedJob, @Variable int goodsRequested) {

        HashMap variables = new HashMap();
        variables.put("gooodsRequested", goodsRequested);

        zeebeClient.newCreateInstanceCommand().bpmnProcessId("Process_000efve").latestVersion().send();

        /*
        zeebeClient.newPublishMessageCommand()
                .messageName("RequestWarehouse")
                .correlationKey("")
                .variables(variables)
                .send();
        */

        zeebeClient.newCompleteCommand(activatedJob.getKey()).send();
    }
}
