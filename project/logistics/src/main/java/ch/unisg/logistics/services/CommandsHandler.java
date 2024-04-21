package ch.unisg.logistics.services;

import ch.unisg.logistics.messages.Message;
import ch.unisg.logistics.messages.ScheduleTransferCommandPayload;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandsHandler {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ObjectMapper objectMapper;

    public void ScheduleTransfer(String messagePayloadJson) throws Exception {
        Message<ScheduleTransferCommandPayload> message = objectMapper.readValue(messagePayloadJson, new TypeReference<>() {});
        ScheduleTransferCommandPayload scheduleTransferCommand = message.getData();

        System.out.println("Scheduling the transfer for " + scheduleTransferCommand.getRefId() + " with corr msg: { "  + message.getCorrelationid() + " }");

        runtimeService.createMessageCorrelation(message.getType())
                .processInstanceBusinessKey(message.getTraceid())
                .setVariable("amount", scheduleTransferCommand.getAmount())
                .setVariable("refId", scheduleTransferCommand.getRefId())
                .setVariable("correlationId", message.getCorrelationid())
                .correlateWithResult();
    }

    public void TransportGoods(String messagePayloadJson) throws Exception {
        Message<ScheduleTransferCommandPayload> message = objectMapper.readValue(messagePayloadJson, new TypeReference<>() {});
        ScheduleTransferCommandPayload transportGoodsCommand = message.getData();

        System.out.println("Go to transfer goods bounded context for " + transportGoodsCommand.getRefId() + " with corr msg: { "  + message.getCorrelationid() + " }");

        runtimeService.createMessageCorrelation(message.getType())
                .processInstanceBusinessKey(message.getTraceid())
                .setVariable("amount", transportGoodsCommand.getAmount())
                .setVariable("refId", transportGoodsCommand.getRefId())
                .setVariable("correlationId", message.getCorrelationid())
                .correlateWithResult();
    }

    public void ConfirmTransfer(String messagePayloadJson) throws Exception {
        Message<ScheduleTransferCommandPayload> message = objectMapper.readValue(messagePayloadJson, new TypeReference<>() {});
        ScheduleTransferCommandPayload transportGoodsCommand = message.getData();

        System.out.println("Go to confirm transport bounded context for " + transportGoodsCommand.getRefId() + " with corr msg: { "  + message.getCorrelationid() + " }");

        runtimeService.createMessageCorrelation(message.getType())
                .processInstanceBusinessKey(message.getTraceid())
                .setVariable("amount", transportGoodsCommand.getAmount())
                .setVariable("refId", transportGoodsCommand.getRefId())
                .setVariable("correlationId", message.getCorrelationid())
                .correlateWithResult();
    }
}
