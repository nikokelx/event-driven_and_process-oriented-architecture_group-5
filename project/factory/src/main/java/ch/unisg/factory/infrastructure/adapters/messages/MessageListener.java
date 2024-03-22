package ch.unisg.factory.infrastructure.adapters.messages;

import ch.unisg.factory.core.services.EventsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MessageListener {

    @Autowired
    private EventsHandler eventsHandler;

    @Transactional
    @KafkaListener(id = "factory", topics = CiraMessageSender.TOPIC_NAME)
    public void messageReceived(String messagePayloadJson, @Header("type") String messageType) throws Exception{
        switch (messageType) {
            case "TransferScheduledEvent" -> eventsHandler.TransferScheduled(messagePayloadJson);
            case "GoodsTransportedEvent" -> eventsHandler.GoodsTransported(messagePayloadJson);
            case "TransferConfirmedEvent" -> eventsHandler.TransferConfirmed(messagePayloadJson);
            default -> {
                System.out.println("Ignoring message in Factory ML of type " + messageType);
            }
        }
    }
}
