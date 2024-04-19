package ch.unisg.factory.core.services;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("DoSomething")
public class DoSomething {

    @Autowired
    private ZeebeClient zeebeClient;

    @JobWorker(type = "orchestrate-something")
    public void doSomething() {
        String message = "message-01";
        System.out.println("HASDLASD AS");
        zeebeClient.newPublishMessageCommand().messageName("Message_3r6ifmt").correlationKey(message).messageId("Message_3r6ifmt").send();

        System.out.println("THIS IS a test.");
    }
}
