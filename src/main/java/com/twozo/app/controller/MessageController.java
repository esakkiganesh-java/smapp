package com.twozo.app.controller;

import com.twozo.app.model.dto.MessageDto;
import com.twozo.app.model.Message;
import com.twozo.app.model.dto.ResponseDto;
import com.twozo.app.service.MessageService;
import com.twozo.app.validation.UserInputValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.Instant;

@RestController
@RequestMapping("/api/messages")
public class MessageController{

    private final MessageService messageService;
    private final UserInputValidator userInputValidator;

    public MessageController(final MessageService messageService,final UserInputValidator userInputValidator) {
        this.messageService = messageService;
        this.userInputValidator = userInputValidator;
    }

    @PostMapping("/send")
    public ResponseEntity<ResponseDto> sendMessage(@RequestBody Message message) {
        message.setSentTimestamp((Instant.now()));
        final int valid = userInputValidator.validateSendMessage(message);

        if(valid == 1){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid! sender Id must be greater than zero"));
        }

        else if(valid == 2){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid! receiver id must be greater than zero"));
        }

        else if(valid == 3){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid! message content should not be empty"));
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

        final int valid = userInputValidator.validateEditMessage(messageDto);

        if(valid == 1){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid! Message id must be greater than zero"));
        }

        else if(valid == 2){
            return ResponseEntity.badRequest().body(new ResponseDto("invalid! message content should not be empty"));
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
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid message id"));
        }

        final boolean result = messageService.deleteMessage(messageDto);

        if(result){
            return ResponseEntity.ok(new ResponseDto("Message deleted successfully"));
        }

        return ResponseEntity.badRequest()
                .body(new ResponseDto("Message Details not found/Message not deleted"));
    }

}

