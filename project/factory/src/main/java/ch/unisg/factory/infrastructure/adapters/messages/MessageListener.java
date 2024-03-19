package ch.unisg.factory.infrastructure.adapters.messages;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MessageListener {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    @KafkaListener(id = "factory", topics = CiraMessageSender.TOPIC_NAME)
    public void messageReceived(String messagePayloadJson, @Header("type") String messageType) throws Exception{
        if (!"TransferScheduledEvent".equals(messageType)) {
            System.out.println("Ignoring message in Factory ML of type " + messageType);
            return;
        }

        Message<TransferScheduledEventPayload> message = objectMapper.readValue(messagePayloadJson, new TypeReference<>() {});
        TransferScheduledEventPayload scheduleTransferCommand = message.getData();

        runtimeService.createMessageCorrelation(message.getType())
                .processInstanceBusinessKey(message.getTraceid())
                .setVariable("refId", scheduleTransferCommand.getRefId())
                .setVariable("correlationId", message.getCorrelationid())
                .correlateWithResult();
    }
}
