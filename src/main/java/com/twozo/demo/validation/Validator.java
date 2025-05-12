package com.twozo.demo.validation;

import com.twozo.demo.model.Dto.MessageDto;
import com.twozo.demo.model.Dto.UserDto;
import com.twozo.demo.model.Message;
import com.twozo.demo.model.User;
import org.springframework.stereotype.Service;

@Service
public interface Validator {

    boolean validateRegister(User user);

    boolean validateUserDelete(UserDto userDto);

    boolean validateUserDetail(String userInfo);

    boolean validateUserDetailUpdates(UserDto userDto);

    boolean validatePasswordUpdate(UserDto userDto);

    boolean validateSendMessage(Message message);

    boolean validateDeleteMessage(int messageId);

    boolean validateEditMessage(MessageDto messageDto);

    boolean validateGetInboxMessages(int id);

    boolean validateGetChatHistory(MessageDto messageDto);
}
