package ch.unisg.logistics.flow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service("ScheduleTransferOfGoodsAdapter")
public class ScheduleTransferOfGoodsAdapter implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        LocalDate dateThreeDaysAhead = LocalDate.now().plusDays(3);
        System.out.println("*** Transfer scheduled for: " + dateThreeDaysAhead + " ***");
    }
}
