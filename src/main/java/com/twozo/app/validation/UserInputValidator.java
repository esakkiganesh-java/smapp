package com.twozo.app.validation;

import com.twozo.app.model.dto.MessageDto;
import com.twozo.app.model.dto.UserDto;
import com.twozo.app.model.Message;
import com.twozo.app.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserInputValidator {

    int validateRegister(final User user);

    int validateUserDelete(final UserDto userDto);

    boolean validateUserDetail(final String userInfo);

    int validateUserDetailUpdates(final UserDto userDto);

    int validatePasswordUpdate(final UserDto userDto);

    int validateSendMessage(final Message message);

    boolean validateDeleteMessage(final int messageId);

    int validateEditMessage(final MessageDto messageDto);

    boolean validateGetInboxMessages(final int id);

    int validateGetChatHistory(final MessageDto messageDto);
}
