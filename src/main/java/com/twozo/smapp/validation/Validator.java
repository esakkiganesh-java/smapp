package com.twozo.smapp.validation;

import com.twozo.smapp.model.dto.MessageDto;
import com.twozo.smapp.model.dto.UserDto;
import com.twozo.smapp.model.Message;
import com.twozo.smapp.model.User;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface Validator {

    Collection<String> validateRegister(final User user);

    Collection<String> validateUserDelete(final UserDto userDto);

    boolean validateUserDetail(final String phNo);

    Collection<String> validateUserDetailUpdates(final UserDto userDto);

    Collection<String> validatePasswordUpdate(final UserDto userDto);

    Collection<String> validateSendMessage(final Message message);

    boolean validateDeleteMessage(final int messageId);

    Collection<String> validateEditMessage(final MessageDto messageDto);

    boolean validateGetInboxMessages(final int id);

    Collection<String> validateGetChatHistory(final MessageDto messageDto);
}
