package com.twozo.smapp.dao;

import java.util.Collection;

import com.twozo.smapp.model.Message;
import com.twozo.smapp.model.dto.MessageDto;
import com.twozo.smapp.model.dto.MessageResponseDto;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDao extends GenericDao<Message, MessageDto> {

	boolean add(final Message message);

	boolean update(final MessageDto messageDto,final int updateType);

	boolean delete(final MessageDto messageDto);

	Collection<MessageResponseDto> getInboxMessages(final int userId);

	Collection<MessageResponseDto> getChatHistory(final MessageDto messageDto);

	void markMessagesAsSeen(final int userId);

}
