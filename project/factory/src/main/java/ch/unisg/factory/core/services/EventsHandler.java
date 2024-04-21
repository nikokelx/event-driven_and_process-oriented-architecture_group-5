package ch.unisg.factory.core.services;

import ch.unisg.factory.infrastructure.adapters.messages.GoodsTransportedEventPayload;
import ch.unisg.factory.infrastructure.adapters.messages.Message;
import ch.unisg.factory.infrastructure.adapters.messages.TransferScheduledEventPayload;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventsHandler {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ObjectMapper objectMapper;

    public void TransferScheduled(String messagePayloadJson) throws Exception {
        Message<TransferScheduledEventPayload> message = objectMapper.readValue(messagePayloadJson, new TypeReference<>() {});
        TransferScheduledEventPayload scheduleTransferCommand = message.getData();

        runtimeService.createMessageCorrelation(message.getType())
                .processInstanceBusinessKey(message.getTraceid())
                .setVariable("refId", scheduleTransferCommand.getRefId())
                .setVariable("correlationId", message.getCorrelationid())
                .correlateWithResult();
    }

    public void GoodsTransported(String messagePayloadJson) throws Exception {
        Message<GoodsTransportedEventPayload> message = objectMapper.readValue(messagePayloadJson, new TypeReference<>() {});
        GoodsTransportedEventPayload scheduleTransferCommand = message.getData();

        runtimeService.createMessageCorrelation(message.getType())
                .processInstanceBusinessKey(message.getTraceid())
                .setVariable("refId", scheduleTransferCommand.getRefId())
                .setVariable("correlationId", message.getCorrelationid())
                .correlateWithResult();
    }

    public void TransferConfirmed(String messagePayloadJson) throws Exception {
        Message<GoodsTransportedEventPayload> message = objectMapper.readValue(messagePayloadJson, new TypeReference<>() {});
        GoodsTransportedEventPayload scheduleTransferCommand = message.getData();

        runtimeService.createMessageCorrelation(message.getType())
                .processInstanceBusinessKey(message.getTraceid())
                .setVariable("refId", scheduleTransferCommand.getRefId())
                .setVariable("correlationId", message.getCorrelationid())
                .correlateWithResult();
    }
}
