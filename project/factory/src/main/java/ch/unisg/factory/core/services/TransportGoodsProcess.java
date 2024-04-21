package ch.unisg.factory.core.services;

import ch.unisg.factory.core.entities.Factory;
import ch.unisg.factory.core.entities.TransferOfGoodsRequest;
import ch.unisg.factory.core.services.commands.ScheduleTransferCommandPayload;
import ch.unisg.factory.core.services.commands.TransportGoodsCommandPayload;
import ch.unisg.factory.infrastructure.adapters.messages.CiraMessageSender;
import ch.unisg.factory.infrastructure.adapters.messages.Message;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("TransportGoodsProcess")
public class TransportGoodsProcess implements JavaDelegate {

    @Autowired
    private CiraMessageSender messageSender;
    @Override
    public void execute(DelegateExecution context) throws Exception {
        // TODO: modify to get the amount from the context not from memory

        Factory factory = Factory.getFactory();
        TransferOfGoodsRequest request = factory.getTransferOfGoodsRequestById(
                (String)context.getVariable("transferOfGoodsRequestId")
        );

        String traceId = context.getProcessBusinessKey();

        messageSender.send(
                new Message<TransportGoodsCommandPayload>(
                        "TransportGoodsCommand",
                        traceId,
                        new TransportGoodsCommandPayload()
                                .setRefId(request.getId().value())
                                .setAmount(request.getGoodsAmount().value())
                )
        );
    }
}
