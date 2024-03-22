package ch.unisg.logistics.flow;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service("TransportGoodsAdapter")
public class TransportGoodsAdapter implements JavaDelegate {

    @Override
    public void execute(DelegateExecution context) throws Exception {
        Random random = new Random();
        int randomNumber = random.nextInt(21);

        if (randomNumber == 13) {
            System.out.println("###### Transport of goods had an accident! ######");
            throw new BpmnError("transport_accident", "Transport had an accident.");
        }

        System.out.println("###### Transport of goods started... ######");
    }
}