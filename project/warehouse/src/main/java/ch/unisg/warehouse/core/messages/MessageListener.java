package ch.unisg.warehouse.core.messages;

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
    @KafkaListener(id = "warehouse", topics = CiraMessageSender.TOPIC_NAME)
    public void messageReceived(String messagePayloadJson, @Header("type") String messageType) throws Exception {
        if (!"ConfirmTransferCommand".equals(messageType)) {
            System.out.println("Ignoring message in Warehouse ML of type " + messageType);
            return;
        }

        Message<ConfirmTransferCommandPayload> message = objectMapper.readValue(messagePayloadJson, new TypeReference<>() {});
        ConfirmTransferCommandPayload scheduleTransferCommand = message.getData();

        runtimeService.createMessageCorrelation(message.getType())
                .processInstanceBusinessKey(message.getTraceid())
                .setVariable("refId", scheduleTransferCommand.getRefId())
                .setVariable("correlationId", message.getCorrelationid())
                .correlateWithResult();
    }
}
