package com.twozo.app.controller;

import com.twozo.app.model.Dto.MessageDto;
import com.twozo.app.model.Message;
import com.twozo.app.model.Dto.ResponseDto;
import com.twozo.app.service.MessageService;
import com.twozo.app.validation.UserInputValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        final boolean valid = userInputValidator.validateSendMessage(message);

        if(!valid){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid details try again"));
        }

        final boolean result = messageService.sendMessage(message);

        if(result){
            return ResponseEntity.ok(new ResponseDto("Message sent successfully"));
        }

        return ResponseEntity.badRequest()
                .body(new ResponseDto("sender or receiver details not found/Message not sent"));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ResponseDto> editMessage(@PathVariable int id, @RequestParam String content) {
        MessageDto messageDto = new MessageDto();
        messageDto.setMessageId(id);
        messageDto.setContent(content);

        final boolean valid = userInputValidator.validateEditMessage(messageDto);

        if(!valid){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid details try again"));
        }

        final boolean result = messageService.editMessage(messageDto);

        if(result){
            return ResponseEntity.ok(new ResponseDto("Message edited successfully"));
        }

        return ResponseEntity.badRequest()
                .body(new ResponseDto("Message details not found/Message not edited"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> deleteMessage(@PathVariable int id) {

        final boolean valid = userInputValidator.validateDeleteMessage(id);

        if(!valid){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid details try again"));
        }

        final boolean result = messageService.deleteMessage(id);

        if(result){
            return ResponseEntity.ok(new ResponseDto("Message deleted successfully"));
        }

        return ResponseEntity.badRequest()
                .body(new ResponseDto("Message Details not found/Message not deleted"));
    }

}

