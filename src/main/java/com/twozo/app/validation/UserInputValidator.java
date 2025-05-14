package com.twozo.app.validation;

import com.twozo.app.model.Dto.MessageDto;
import com.twozo.app.model.Dto.UserDto;
import com.twozo.app.model.Message;
import com.twozo.app.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserInputValidator {

    boolean validateRegister(final User user);

    boolean validateUserDelete(final UserDto userDto);

    boolean validateUserDetail(final String userInfo);

    boolean validateUserDetailUpdates(final UserDto userDto);

    boolean validatePasswordUpdate(final UserDto userDto);

    boolean validateSendMessage(final Message message);

    boolean validateDeleteMessage(final int messageId);

    boolean validateEditMessage(final MessageDto messageDto);

    boolean validateGetInboxMessages(final int id);

    boolean validateGetChatHistory(final MessageDto messageDto);
}
