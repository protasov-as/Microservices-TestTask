package com.example.service1.other;

import com.example.service1.entity.MessageEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.*;

import java.lang.reflect.Type;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    private Logger logger = LogManager.getLogger(MyStompSessionHandler.class);

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        logger.info("New session established : " + session.getSessionId());
        session.subscribe("/topic/messages", this);
        logger.info("Subscribed to /topic/messages");
        session.send("/app/chat", getSampleMessage());
        logger.info("Message sent to websocket server");
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Got an exception", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return MessageEntity.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        MessageEntity msg = (MessageEntity) payload;
        logger.info("Received : " + msg.getId() + " from : " + msg.getMc1Timestamp());
    }

    /**
     * A sample message instance.
     * @return instance of <code>Message</code>
     */
    private MessageEntity getSampleMessage() {
        MessageEntity msg = new MessageEntity();
        msg.setId(11111111);
        msg.setSessionId(777777);
        return msg;
    }
}
