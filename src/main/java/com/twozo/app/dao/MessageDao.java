package com.twozo.app.dao;

import java.util.Collection;
import com.twozo.app.model.dto.MessageResponseDto;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDao<Message,MessageDto> extends GeneralDao<Message,MessageDto>{

	@Override
	boolean add(final Message message);

	@Override
	boolean update(final MessageDto messageDto,final int updateType);

	@Override
	boolean delete(final MessageDto messageDto);

	Collection<MessageResponseDto> getInboxMessages(final int userId);

	Collection<MessageResponseDto> getChatHistory(final MessageDto messageDto);
}
