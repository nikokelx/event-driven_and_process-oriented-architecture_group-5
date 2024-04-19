package ch.unisg.factory.core.services;

import ch.unisg.factory.core.entities.Machine;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("start-production-line")
public class StartProductionLineProcess {

    @Autowired
    private ZeebeClient client;

    @JobWorker(type = "start-production-line")
    public void startProductionLineProcess() {

        client.newPublishMessageCommand().messageName("Message_3r6ifmt").correlationKey("orderId").messageId("Message_3r6ifmt").send();

        System.out.println("HELLO MY FREIND from the other side");


    }
}
