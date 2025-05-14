package com.twozo.app.controller;

import com.twozo.app.model.Dto.MessageDto;
import com.twozo.app.model.Dto.MessageResponseDto;
import com.twozo.app.service.InboxService;
import com.twozo.app.validation.UserInputValidator;
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
    private final UserInputValidator userInputValidator;

    public InboxController(final InboxService inboxService,final UserInputValidator userInputValidator) {
        this.inboxService = inboxService;
        this.userInputValidator = userInputValidator;
    }


    @PostMapping("/inboxMessages")
    public ResponseEntity<?> getInboxMessages(@RequestParam int id){
       final boolean valid = userInputValidator.validateGetInboxMessages(id);

        if(!valid){
            return ResponseEntity.badRequest().body("Invalid user ID");
        }

        final Collection<MessageResponseDto> inboxMessages = inboxService.getInboxHistory(id);

        final Collection<MessageResponseDto> responseList = new ArrayList<>();

        for(MessageResponseDto msg : inboxMessages) {
            responseList.add(new MessageResponseDto(msg.getMessageId(), msg.getReceiverName(), msg.getMessageContent(), msg.getSenderName(), msg.getSentTimeStamp(), msg.getStatus()));
        }

        return ResponseEntity.ok(responseList);
    }

    @PostMapping("/chatHistory")
    public ResponseEntity<?> getChatHistory(@RequestBody MessageDto messageDto) {
        final boolean valid = userInputValidator.validateGetChatHistory(messageDto);

        if(!valid){
            return ResponseEntity.badRequest().body("Invalid sender or receiver");
        }

        final Collection<MessageResponseDto> chatHistory = inboxService.getChatHistory(messageDto);
        final Collection<MessageResponseDto> responseList = new ArrayList<>();

        for(MessageResponseDto msg : chatHistory) {
            responseList.add(new MessageResponseDto(msg.getMessageId(), msg.getReceiverName(), msg.getMessageContent(), msg.getSenderName(), msg.getSentTimeStamp(), msg.getStatus()));
        }

        return ResponseEntity.ok(responseList);
    }
}

