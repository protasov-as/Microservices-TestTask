package com.example.service1.controller;

import com.example.service1.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@org.springframework.stereotype.Controller
public class MessageController {

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/start")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> mainPage() {
        if(messageService.isProcessRunning()) {
            return ResponseEntity.status(HttpStatus.OK).body("Task is already running");
        } else {
            messageService.startCommunication();
            return ResponseEntity.status(HttpStatus.OK).body("Task is successfully started");
        }
    }

    @GetMapping("/stop")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> stopProcess() {
        if(!messageService.isProcessRunning()) {
            return ResponseEntity.status(HttpStatus.OK).body("Task is not running");
        } else {
            messageService.stopProcess();
            return ResponseEntity.status(HttpStatus.OK).body("Task is successfully stopped");
        }
    }

    @PostMapping("/postMsg")
    public ResponseEntity<String> getMessage(@RequestBody String incomingMessage) throws JsonProcessingException {
//        System.out.println("Got msg " + incomingMessage);
        messageService.processMessageFromService3(incomingMessage);
        return ResponseEntity.status(HttpStatus.CREATED).body("Message saved!");
    }

    @GetMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public void trySend() {
        messageService.sendMessage();
    }
}
