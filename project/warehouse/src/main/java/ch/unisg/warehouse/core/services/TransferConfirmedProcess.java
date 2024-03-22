package ch.unisg.warehouse.core.services;

import ch.unisg.warehouse.core.messages.CiraMessageSender;
import ch.unisg.warehouse.core.messages.Message;
import ch.unisg.warehouse.core.messages.TransferConfirmedEventPayload;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("TransferConfirmedProcess")
public class TransferConfirmedProcess implements JavaDelegate {

    @Autowired
    private CiraMessageSender messageSender;

    @Override
    public void execute(DelegateExecution context) throws Exception {
        String refId = (String) context.getVariable("refId");
        String correlationId = (String) context.getVariable("correlationId");
        String traceId = context.getProcessBusinessKey();

        messageSender.send(
                new Message<TransferConfirmedEventPayload>(
                        "TransferConfirmedEvent",
                        traceId,
                        new TransferConfirmedEventPayload()
                                .setRefId(refId))
                        .setCorrelationid(correlationId));
    }
}
