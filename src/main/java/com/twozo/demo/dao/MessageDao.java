package com.twozo.demo.dao;

import java.util.Collection;

import com.twozo.demo.model.Dto.MessageDto;
import com.twozo.demo.model.Dto.MessageResponseDto;
import com.twozo.demo.model.Message;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDao {

	boolean addMessage(Message message);

	boolean editMessage(MessageDto messageDto);

	boolean deleteMessage(int messageId);

	Collection<MessageResponseDto> getInboxMessages(int userId);

	Collection<MessageResponseDto> getChatHistory(MessageDto messageDto);
}
