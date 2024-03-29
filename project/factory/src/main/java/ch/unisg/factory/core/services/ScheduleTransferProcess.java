package ch.unisg.factory.core.services;

import ch.unisg.factory.core.entities.Factory;
import ch.unisg.factory.core.entities.TransferOfGoodsRequest;
import ch.unisg.factory.core.services.commands.ScheduleTransferCommandPayload;
import ch.unisg.factory.infrastructure.adapters.messages.CiraMessageSender;
import ch.unisg.factory.infrastructure.adapters.messages.Message;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ScheduleTransferProcess")
public class ScheduleTransferProcess implements JavaDelegate {

    @Autowired
    private CiraMessageSender messageSender;

    @Override
    public void execute(DelegateExecution context) throws Exception {
        Factory factory = Factory.getFactory();
        TransferOfGoodsRequest request = factory.getTransferOfGoodsRequestById(
                (String)context.getVariable("transferOfGoodsRequestId")
        );

        String traceId = context.getProcessBusinessKey();

        messageSender.send(
                new Message<ScheduleTransferCommandPayload>(
                        "ScheduleTransferCommand",
                        traceId,
                        new ScheduleTransferCommandPayload()
                                .setRefId(request.getId().value())
                                .setAmount(request.getGoodsAmount().value())
                )
        );
    }
}
