package ch.unisg.logistics.services.camunda;

import com.google.errorprone.annotations.Var;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("transport")
public class TransportTask {
    @Autowired
    private ZeebeClient zeebeClient;

    @JobWorker(type = "tansport-task", autoComplete = false)
    public void transport(ActivatedJob activatedJob, @Variable int acc) throws InterruptedException {

        if (acc == 0) {
            Thread.sleep(15000);
        } else if (acc == 1) {
            Thread.sleep(12000);

        } else if (acc == 2) {
            Thread.sleep(10000);
        } else {
            zeebeClient.newCompleteCommand(activatedJob.getKey()).send();
        }
    }
}
