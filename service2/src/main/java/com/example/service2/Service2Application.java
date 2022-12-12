package com.example.service2;

import com.example.service2.service.MySessionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Scanner;

@SpringBootApplication
public class Service2Application {


	public static void main(String[] args) {
		SpringApplication.run(Service2Application.class, args);

		WebSocketClient webSocketClient = new StandardWebSocketClient();
		WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setObjectMapper(new ObjectMapper().registerModule(new JavaTimeModule()));
		stompClient.setMessageConverter(converter);
		stompClient.setTaskScheduler(new ConcurrentTaskScheduler());

		String url = "ws://service1:8081/chat";
		StompSessionHandler sessionHandler = new MySessionHandler();
		stompClient.connect(url, sessionHandler);

	}
}
