package com.twozo.smapp.service;

import com.twozo.smapp.model.dto.MessageDto;
import com.twozo.smapp.model.Message;
import com.twozo.smapp.model.dto.MessageResponseDto;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface MessageService {

	boolean sendMessage(final Message message);

	boolean editMessage(final MessageDto messageDto,int updateType);

	boolean deleteMessage(final MessageDto messageDto);

	Collection<MessageResponseDto> getInboxHistory(final int userId);

	Collection<MessageResponseDto> getChatHistory(final MessageDto messageDto);

}
