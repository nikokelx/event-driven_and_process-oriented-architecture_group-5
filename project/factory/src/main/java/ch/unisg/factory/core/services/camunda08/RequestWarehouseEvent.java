package ch.unisg.factory.core.services.camunda08;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("request-warehouse")
public class RequestWarehouseEvent {

    @Autowired
    private ZeebeClient zeebeClient;

    @JobWorker(type = "request-warehouse-event", autoComplete = false)
    public void requestWarehouseEvent(ActivatedJob activatedJob) {
         System.out.println("########################### Factory --> request-warehouse-event");

         zeebeClient.newCreateInstanceCommand().bpmnProcessId("Process_1qc7kt6").latestVersion().send();
         // zeebeClient.newPublishMessageCommand().messageName("RequestFactory").correlationKey("request-warehouse").send();
         zeebeClient.newCompleteCommand(activatedJob.getKey()).send();
     }
}