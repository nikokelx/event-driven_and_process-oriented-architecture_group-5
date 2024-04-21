package ch.unisg.logistics.messages;

import ch.unisg.logistics.services.CommandsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MessageListener {

    @Autowired
    private CommandsHandler commandsHandler;

    @Transactional
    @KafkaListener(id = "logistics", topics = CiraMessageSender.TOPIC_NAME)
    public void messageReceived(String messagePayloadJson, @Header("type") String messageType) throws Exception {
        switch (messageType) {
            case "ScheduleTransferCommand" -> commandsHandler.ScheduleTransfer(messagePayloadJson);
            case "TransportGoodsCommand" -> commandsHandler.TransportGoods(messagePayloadJson);
            case "ConfirmTransferCommand" -> commandsHandler.ConfirmTransfer(messagePayloadJson);
            default -> {
                System.out.println("Ignoring message in Logistics ML of type " + messageType);
            }
        }
    }
}
