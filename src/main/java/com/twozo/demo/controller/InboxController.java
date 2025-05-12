package com.twozo.demo.controller;

import com.twozo.demo.model.Dto.MessageDto;
import com.twozo.demo.model.Dto.MessageResponseDto;
import com.twozo.demo.service.InboxService;
import com.twozo.demo.validation.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.ArrayList;
import java.util.Collection;



@RestController
@RequestMapping("/inbox")
public class InboxController {

    private final InboxService inboxService;
    private final Validator validator;


    public InboxController(InboxService inboxService, Validator validator) {
        this.inboxService = inboxService;
        this.validator = validator;
    }


    @PostMapping("/getInboxMessages")
    public ResponseEntity<?> getInboxMessages(@RequestParam int id){
        boolean valid = validator.validateGetInboxMessages(id);
        if(!valid){
            return ResponseEntity.badRequest().body("Invalid user ID");
        }
        Collection<MessageResponseDto> inboxMessages = inboxService.getInboxHistory(id);

        Collection<MessageResponseDto> responseList = new ArrayList<>();
        for (MessageResponseDto msg : inboxMessages) {
            responseList.add(new MessageResponseDto(
                    msg.getMessageId(),
                    msg.getReceiverName(),
                    msg.getMessageContent(),
                    msg.getSenderName(),
                    msg.getSentTimeStamp(),
                    msg.getStatus()
            ));
        }

        return ResponseEntity.ok(responseList);
    }

    @PostMapping("/getChatHistory")
    public ResponseEntity<?> getChatHistory(@RequestBody MessageDto messageDto) {
        boolean valid = validator.validateGetChatHistory(messageDto);
        if(!valid){
            return ResponseEntity.badRequest().body("Invalid sender or receiver");
        }
        Collection<MessageResponseDto> chatHistory = inboxService.getChatHistory(messageDto);

        Collection<MessageResponseDto> responseList = new ArrayList<>();
        for (MessageResponseDto msg : chatHistory) {
            responseList.add(new MessageResponseDto(
                    msg.getMessageId(),
                    msg.getReceiverName(),
                    msg.getMessageContent(),
                    msg.getSenderName(),
                    msg.getSentTimeStamp(),
                    msg.getStatus()
            ));
        }

        return ResponseEntity.ok(responseList);
    }
}

