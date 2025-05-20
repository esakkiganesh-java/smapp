package com.twozo.smapp.controller;

import com.twozo.smapp.model.dto.MessageDto;
import com.twozo.smapp.model.Message;
import com.twozo.smapp.model.dto.MessageResponseDto;
import com.twozo.smapp.model.dto.ResponseDto;
import com.twozo.smapp.model.dto.UserDto;
import com.twozo.smapp.service.MessageService;
import com.twozo.smapp.validation.Validator;
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
    private final Validator userInputValidator;

    public MessageController(final MessageService messageService, final Validator userInputValidator) {
        this.messageService = messageService;
        this.userInputValidator = userInputValidator;
    }

    @PostMapping("/send")
    public ResponseEntity<ResponseDto> sendMessage(@RequestBody Message message) {
        message.setSentTimestamp((Instant.now()));
        final Collection<String> invalidData = userInputValidator.validateSendMessage(message);

        if(!invalidData.isEmpty()){
            return ResponseEntity.badRequest().body(new ResponseDto(invalidData.toString()));
        }

        final boolean result = messageService.sendMessage(message);

        if(result){
            return ResponseEntity.ok(new ResponseDto("Message sent successfully"));
        }

        return ResponseEntity.badRequest()
                .body(new ResponseDto("sender or receiver details not found/Message not sent"));
    }

    @PutMapping("/edit")
    public ResponseEntity<ResponseDto> editMessage(@RequestBody MessageDto messageDto) {

        final Collection<String> invalidData = userInputValidator.validateEditMessage(messageDto);

        if(!invalidData.isEmpty()){
            return ResponseEntity.badRequest().body(new ResponseDto(invalidData.toString()));
        }

        final boolean result = messageService.editMessage(messageDto,0);

        if(result){
            return ResponseEntity.ok(new ResponseDto("Message edited successfully"));
        }

        return ResponseEntity.badRequest()
                .body(new ResponseDto("Message details not found/Message not edited"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteMessage(@RequestBody MessageDto messageDto) {

        final boolean valid = userInputValidator.validateDeleteMessage(messageDto.getId());

        if(!valid){
            return ResponseEntity.badRequest().body(new ResponseDto("invalid message id"));
        }

        final boolean result = messageService.deleteMessage(messageDto);

        if(result){
            return ResponseEntity.ok(new ResponseDto("Message deleted successfully"));
        }

        return ResponseEntity.badRequest().body(new ResponseDto("Message Details not found/Message not deleted"));
    }

    @PostMapping("/inboxMessages")
    public ResponseEntity<?> getInboxMessages(@RequestBody UserDto userDto){
        final boolean valid = userInputValidator.validateGetInboxMessages(userDto.getId());

        if(!valid){
            return ResponseEntity.badRequest().body(new ResponseDto("Invalid user id must be greater than zero"));
        }

        final Collection<MessageResponseDto> inboxMessages = messageService.getInboxHistory(userDto.getId());
        final Collection<MessageResponseDto> responseList = new ArrayList<>();

        for(MessageResponseDto msg : inboxMessages) {
            responseList.add(new MessageResponseDto(msg.getId(), msg.getReceiverName(), msg.getContent(), msg.getSenderName(), msg.getSentTimeStamp(), msg.getStatus()));
        }

        return ResponseEntity.ok(responseList);
    }

    @PostMapping("/chatHistory")
    public ResponseEntity<?> getChatHistory(@RequestBody MessageDto messageDto) {
        final Collection<String> invalidData = userInputValidator.validateGetChatHistory(messageDto);

        if(!invalidData.isEmpty()){
            return ResponseEntity.badRequest().body(new ResponseDto(invalidData.toString()));
        }

        final Collection<MessageResponseDto> chatHistory = messageService.getChatHistory(messageDto);
        final Collection<MessageResponseDto> responseList = new ArrayList<>();

        for(MessageResponseDto msg : chatHistory) {
            responseList.add(new MessageResponseDto(msg.getId(), msg.getReceiverName(), msg.getContent(), msg.getSenderName(), msg.getSentTimeStamp(), msg.getStatus()));
        }

        return ResponseEntity.ok(responseList);
    }
}

