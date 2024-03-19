package ch.unisg.logistics.messages;

import org.camunda.bpm.engine.RuntimeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @KafkaListener(id = "logistics", topics = CiraMessageSender.TOPIC_NAME)
    public void messageReceived(String messagePayloadJson, @Header("type") String messageType) throws Exception{
        if (!"ScheduleTransferCommand".equals(messageType)) {
            System.out.println("Ignoring message in Logistics ML of type " + messageType);
            return;
        }

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
}
