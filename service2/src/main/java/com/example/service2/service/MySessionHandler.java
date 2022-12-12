package com.example.service2.service;

import com.example.service2.config.KafkaProducerConfig;
import com.example.service2.entity.MessageEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class MySessionHandler extends StompSessionHandlerAdapter {

    String topic = "MessagesTest";
    KafkaProducerConfig kafkaProducerConfig = new KafkaProducerConfig();
    ProducerFactory<String, MessageEntity> messageProducerFactory = kafkaProducerConfig.messageProducerFactory();
    KafkaTemplate<String, MessageEntity> kafkaTemplateMessage = new KafkaTemplate<>(messageProducerFactory);

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/topic/messages", this);
//        session.send("/app/hello", "{\"name\":\"Client\"}".getBytes());

        System.out.println("New session: {}" + session.getSessionId());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        exception.printStackTrace();
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return MessageEntity.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        MessageEntity messageEntity = (MessageEntity) payload;
        messageEntity.setMc3Timestamp(LocalDateTime.now());
        kafkaTemplateMessage.send(topic, messageEntity);
    }

}
