package com.example.service1.service;

import com.example.service1.entity.MessageEntity;
import com.example.service1.repositary.MessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private MessageSendingOperations<String> simpMessagingTemplate;
    private KafkaTemplate<String, MessageEntity> kafkaTemplateMessage;

    @Value("${service1.timelimit}")
    long secondsToRun;
    @Value("${service1.topic}")
    String topic;
    LocalDateTime timeStart;
    long timeForTimer;
    int sessionId;
    boolean processIsRunning;
    long numberOfSentMessages;

    @Autowired
    public MessageService(MessageRepository messageRepository, MessageSendingOperations<String> simpMessagingTemplate, KafkaTemplate<String, MessageEntity> kafkaTemplateMessage) {
        this.messageRepository = messageRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.kafkaTemplateMessage = kafkaTemplateMessage;
    }

    public void stopProcess() {
        timeStart = LocalDateTime.now().minusSeconds(secondsToRun);
    }

    public boolean isProcessRunning() {
        return processIsRunning;
    }

    public void sendMessage(){
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setId(1);
        messageEntity.setSessionId(1456);
        messageEntity.setMc1Timestamp(LocalDateTime.now());
        simpMessagingTemplate.convertAndSend("/topic/messages", messageEntity);
    }

    public void sendMessage(MessageEntity messageToService2){
        simpMessagingTemplate.convertAndSend("/topic/messages", messageToService2);
    }

    public void startCommunication(){
        timeStart = LocalDateTime.now();
        timeForTimer = System.currentTimeMillis();
        numberOfSentMessages = 0;

        processIsRunning = true;
        Logger.getLogger(MessageService.class.getName()).info("Communication started!");
        sendMessageToService2(true);
    }

    private void sendMessageToService2(boolean isMessageFirst) {
        if(timeStart.plusSeconds(secondsToRun).isAfter(LocalDateTime.now())) {
            processIsRunning = true;
            numberOfSentMessages++;
            if (isMessageFirst) {
                sendMessage(findLastOrCreateMessageEntity());
            } else {
                MessageEntity consequentMessage = new MessageEntity();
                consequentMessage.setSessionId(sessionId);
                consequentMessage.setMc1Timestamp(LocalDateTime.now());
                sendMessage(consequentMessage);
            }
//            kafkaTemplateMessage.send(topic, findLastOrCreateMessageEntity());
        }
        else {
            processIsRunning = false;
            Logger.getLogger(MessageService.class.getName()).info("Time is over!");
            Logger.getLogger(MessageService.class.getName()).info("Time elapsed: " + ((System.currentTimeMillis() - timeForTimer) / 1000) + " seconds");
            Logger.getLogger(MessageService.class.getName()).info("Number of messages sent: " + numberOfSentMessages);
        }
    }

    public void processMessageFromService3(String message) throws JsonProcessingException {
        MessageEntity messageToSave = convertStringToMessageEntity(message);
        messageToSave.setEndTimestamp(LocalDateTime.now());
        messageRepository.save(messageToSave);
        Logger.getLogger(MessageService.class.getName()).info("Message saved to DB, ID: " + messageRepository.findById(messageToSave.getId()).get().getId());

        sendMessageToService2(false);
    }

    private MessageEntity findLastOrCreateMessageEntity() {
        MessageEntity msg = new MessageEntity();

        if(messageRepository.findFirstByOrderByIdDesc().isEmpty()){
            msg.setId(1);
            sessionId = 1;
            msg.setSessionId(sessionId);
        } else {
            MessageEntity msgFromDb = messageRepository.findFirstByOrderByIdDesc().get();
            msg.setId(msgFromDb.getId() +1);
            sessionId = msgFromDb.getSessionId() == null ? 1 : msgFromDb.getSessionId()+1;
            msg.setSessionId(sessionId);
        }
        msg.setMc1Timestamp(LocalDateTime.now());
        return msg;
    }

    private MessageEntity convertStringToMessageEntity(String input) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(input, MessageEntity.class);
    }
}
