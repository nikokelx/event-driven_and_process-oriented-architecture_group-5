package ch.unisg.logistics.flow;

import ch.unisg.logistics.messages.CiraMessageSender;
import ch.unisg.logistics.messages.GoodsTransportedEventPayload;
import ch.unisg.logistics.messages.Message;
import ch.unisg.logistics.messages.TransferScheduledEventPayload;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("TransferScheduledProcess")
public class GoodsTransportedProcess implements JavaDelegate {

    @Autowired
    private CiraMessageSender messageSender;

    @Override
    public void execute(DelegateExecution context) throws Exception {
        String refId = (String) context.getVariable("refId");
        String correlationId = (String) context.getVariable("correlationId");
        String traceId = context.getProcessBusinessKey();

        messageSender.send(
                new Message<GoodsTransportedEventPayload>(
                        "GoodsTransportedEvent",
                        traceId,
                        new GoodsTransportedEventPayload()
                                .setRefId(refId))
                        .setCorrelationid(correlationId));
    }
}
