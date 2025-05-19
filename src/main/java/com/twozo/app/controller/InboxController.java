package com.twozo.app.controller;

import com.twozo.app.model.dto.MessageDto;
import com.twozo.app.model.dto.MessageResponseDto;
import com.twozo.app.model.dto.ResponseDto;
import com.twozo.app.model.dto.UserDto;
import com.twozo.app.service.InboxService;
import com.twozo.app.validation.UserInputValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<?> getInboxMessages(@RequestBody UserDto userDto){
       final boolean valid = userInputValidator.validateGetInboxMessages(userDto.getId());

        if(!valid){
            return ResponseEntity.badRequest().body(new ResponseDto("Invalid user id must be greater than zero"));
        }

        final Collection<MessageResponseDto> inboxMessages = inboxService.getInboxHistory(userDto.getId());

        final Collection<MessageResponseDto> responseList = new ArrayList<>();

        for(MessageResponseDto msg : inboxMessages) {
            responseList.add(new MessageResponseDto(msg.getMessageId(), msg.getReceiverName(), msg.getMessageContent(), msg.getSenderName(), msg.getSentTimeStamp(), msg.getStatus()));
        }

        return ResponseEntity.ok(responseList);
    }

    @PostMapping("/chatHistory")
    public ResponseEntity<?> getChatHistory(@RequestBody MessageDto messageDto) {
        final int valid = userInputValidator.validateGetChatHistory(messageDto);

        if(valid == 1) {
            return ResponseEntity.badRequest().body(new ResponseDto("Invalid! sender id must be greater than zero"));
        }

        else if(valid == 2) {
            return ResponseEntity.badRequest().body(new ResponseDto("Invalid! receiver id must be greater than zero"));
        }

        final Collection<MessageResponseDto> chatHistory = inboxService.getChatHistory(messageDto);
        final Collection<MessageResponseDto> responseList = new ArrayList<>();

        for(MessageResponseDto msg : chatHistory) {
            responseList.add(new MessageResponseDto(msg.getMessageId(), msg.getReceiverName(), msg.getMessageContent(), msg.getSenderName(), msg.getSentTimeStamp(), msg.getStatus()));
        }

        return ResponseEntity.ok(responseList);
    }
}

