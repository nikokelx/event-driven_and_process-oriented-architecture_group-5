package ch.unisg.factory.core.services;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Service;

@Service("DoSomething")
public class DoSomething {

    @JobWorker(type = "orchestrate-something")
    public void doSomething() {
        System.out.println("THIS IS a test.");
    }
}
