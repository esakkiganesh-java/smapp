package com.twozo.app.service;

import com.twozo.app.model.Dto.MessageDto;
import com.twozo.app.model.Message;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {

	boolean sendMessage(final Message message);

	boolean editMessage(final MessageDto messageDto);

	boolean deleteMessage(final int messageId);
}
