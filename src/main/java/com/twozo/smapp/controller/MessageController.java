package com.twozo.smapp.controller;

import com.twozo.smapp.model.User;
import com.twozo.smapp.model.Message;
import com.twozo.smapp.model.ApiResponse;
import com.twozo.smapp.service.MessageService;
import com.twozo.smapp.validation.MessageValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/api/messages")
public class MessageController{

    private final MessageService messageService;
    private final MessageValidator messageValidator;

    public MessageController(final MessageService messageService, final MessageValidator messageValidator) {
        this.messageService = messageService;
        this.messageValidator = messageValidator;
    }

    @PostMapping("/send")
    public ResponseEntity<ApiResponse> send(@RequestBody Message message) {
        message.setSentTimestamp((Instant.now().toString()));
        final int validationType = 1;
        final Collection<String>  errors = messageValidator.validate(message,validationType);

        if(!errors.isEmpty()){
            return ResponseEntity.badRequest().body(new ApiResponse(errors.toString()));
        }

        final boolean result = messageService.send(message);

        if(result){
            return ResponseEntity.ok(new ApiResponse("Message sent successfully"));
        }

        return ResponseEntity.badRequest()
                .body(new ApiResponse("sender or receiver details not found/Message not sent"));
    }

    @PutMapping("/edit")
    public ResponseEntity<ApiResponse> edit(@RequestBody Message message) {
        final int validationType = 2;
        final Collection<String> errors = messageValidator.validate(message,validationType);

        if(!errors.isEmpty()){
            return ResponseEntity.badRequest().body(new ApiResponse(errors.toString()));
        }

        final boolean result = messageService.edit(message,0);

        if(result){
            return ResponseEntity.ok(new ApiResponse("Message edited successfully"));
        }

        return ResponseEntity.badRequest()
                .body(new ApiResponse("Message details not found/Message not edited"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> delete(@RequestBody Message message) {
        final int validationType = 3;
        final Collection<String> errors = messageValidator.validate(message,validationType);

        if(!errors.isEmpty()){
            return ResponseEntity.badRequest().body(new ApiResponse(errors.toString()));
        }

        final boolean result = messageService.delete(message);

        if(result){
            return ResponseEntity.ok(new ApiResponse("Message deleted successfully"));
        }

        return ResponseEntity.badRequest().body(new ApiResponse("Message Details not found/Message not deleted"));
    }

    @PostMapping("/inboxMessages")
    public ResponseEntity<?> getInbox(@RequestBody User user){
        final int validationType = 3;
        final Message message = new Message();
        message.setId(user.getId());
        final Collection<String> errors = messageValidator.validate(message,validationType);

        if(!errors.isEmpty()){
            return ResponseEntity.badRequest().body(new ApiResponse(errors.toString()));
        }

        final Collection<Message> inboxMessages = messageService.getInbox(user.getId());
        final Collection<Message> responseList = new ArrayList<>();

        for(Message msg : inboxMessages) {
            responseList.add(new Message(msg.getId(), msg.getReceiverId(),msg.getReceiverName(), msg.getContent(), msg.getSenderId(), msg.getSenderName(), msg.getSentTimestamp(), msg.getStatus()));
        }

        return ResponseEntity.ok(responseList);
    }

    @PostMapping("/chatHistory")
    public ResponseEntity<?> getChatHistory(@RequestBody Message message) {
        final int validationType = 4;
        final Collection<String> errors = messageValidator.validate(message,validationType);

        if(!errors.isEmpty()){
            return ResponseEntity.badRequest().body(new ApiResponse(errors.toString()));
        }

        final Collection<Message> chatHistory = messageService.getChatHistory(message);
        final Collection<Message> responseList = new ArrayList<>();

        for(Message msg : chatHistory) {
            responseList.add(new Message(msg.getId(), msg.getReceiverId(),msg.getReceiverName(), msg.getContent(), msg.getSenderId(), msg.getSenderName(), msg.getSentTimestamp(), msg.getStatus()));
        }

        return ResponseEntity.ok(responseList);
    }
}

