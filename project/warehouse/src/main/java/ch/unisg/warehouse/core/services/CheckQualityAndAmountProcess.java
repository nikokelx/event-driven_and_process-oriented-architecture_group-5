package ch.unisg.warehouse.core.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("CheckQualityAndAmountProcess")
public class CheckQualityAndAmountProcess implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("###### Checking the quality and amount of transported goods... ######");
    }
}
