package ch.unisg.warehouse.core.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("IncreaseStockLevelProcess")
public class AssignStockIdProcess implements JavaDelegate {
    @Override
    public void execute(DelegateExecution context) throws Exception {

    }
}
