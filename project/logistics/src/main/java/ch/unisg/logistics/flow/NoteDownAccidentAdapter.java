package ch.unisg.logistics.flow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("NoteDownAccidentAdapter")
public class NoteDownAccidentAdapter implements JavaDelegate {

    @Override
    public void execute(DelegateExecution context) throws Exception {
        System.out.println("###### Transport of goods had an accident! ######");
    }
}