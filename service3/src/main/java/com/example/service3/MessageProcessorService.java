package com.example.service3;

import com.example.service3.entity.MessageEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
public class MessageProcessorService {

    @Value("${service3.endpoint-to-send}")
    private String endpointToSend;

    @KafkaListener(topics = "MessagesTest")
    public void listenWithHeaders(@Payload String message) throws IOException, InterruptedException {
        Logger.getLogger(MessageProcessorService.class.getName()).info("Received Message: " + message);

        MessageEntity messageToSend = convertStringToMessageEntity(message);
        messageToSend.setMc3Timestamp(LocalDateTime.now());

        ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(messageToSend);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpointToSend))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

    }

    private MessageEntity convertStringToMessageEntity(String input) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(input, MessageEntity.class);
    }
}
