package com.twozo.smapp.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import com.twozo.smapp.validation.UserValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.twozo.smapp.model.ApiResponse;
import com.twozo.smapp.model.InboxInfo;
import com.twozo.smapp.model.Message;
import com.twozo.smapp.model.User;
import com.twozo.smapp.service.MessageService;
import com.twozo.smapp.validation.MessageValidator;
import com.twozo.smapp.validation.ValidationType;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService messageService;
    private final MessageValidator messageValidator;
    private final UserValidator userValidator;

    public MessageController(final MessageService messageService, final MessageValidator messageValidator,final UserValidator userValidator) {
        this.messageService = messageService;
        this.messageValidator = messageValidator;
        this.userValidator = userValidator;
    }

    @PostMapping("/send")
    public ResponseEntity<ApiResponse> send(@RequestBody final Message message) {
        message.setSentTimestamp((Instant.now()));
        final Collection<String> errors = messageValidator.validate(message, ValidationType.ADD);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse(errors.toString()));
        }

        messageService.send(message);

        return ResponseEntity.ok().body(new ApiResponse("Message sent.."));
    }

    @PutMapping("/edit")
    public ResponseEntity<ApiResponse> edit(@RequestBody final Message message) {
        final Collection<String> errors = messageValidator.validate(message, ValidationType.UPDATE);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse(errors.toString()));
        }

        messageService.edit(message, "edit");

        return ResponseEntity.ok().body(new ApiResponse("Message edited successfully!"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> delete(@RequestBody final Message message) {
        final Collection<String> errors = messageValidator.validate(message, ValidationType.CHECK_ID);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse(errors.toString()));
        }

        messageService.delete(message);

        return ResponseEntity.ok().body(new ApiResponse("Message deleted"));
    }

    @PostMapping("/inbox")
    public ResponseEntity<?> getInbox(@RequestBody final User user) {
        final Collection<String> errors = userValidator.validate(user, ValidationType.CHECK_ID);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse(errors.toString()));
        }

        final Collection<InboxInfo> chat = messageService.getInbox(user.getId());
        final Collection<InboxInfo> responseList = new ArrayList<>();

        for (final InboxInfo chatInfo : chat) {
            responseList.add(new InboxInfo(chatInfo.getUserId(), chatInfo.getUserName(), chatInfo.getUnreadMessageCount()));
        }

        return ResponseEntity.ok(responseList);
    }

    @PostMapping("/chatHistory")
    public ResponseEntity<?> getChatHistory(@RequestParam final int senderId, @RequestParam final int receiverId) {
        final Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        final Collection<String> errors = messageValidator.validate(message, ValidationType.GET_CHAT);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse(errors.toString()));
        }

        final Collection<Message> chatHistory = messageService.getChatHistory(senderId,receiverId);

        return ResponseEntity.ok(chatHistory);
    }

    @GetMapping("getReport")
    public ResponseEntity<Collection<Message>> getReport(){
        final Collection<Message> messageReport = messageService.getMessageReport();
        return ResponseEntity.ok(messageReport);
    }
}

