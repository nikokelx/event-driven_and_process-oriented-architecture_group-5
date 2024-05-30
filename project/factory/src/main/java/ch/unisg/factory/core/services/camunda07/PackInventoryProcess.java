package ch.unisg.factory.core.services.camunda07;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("PackInventoryProcess")
public class PackInventoryProcess implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("<-- Packaging inventory to transfer completed -->");
    }
}
