package com.twozo.app.dao;

import java.util.Collection;
import com.twozo.app.model.Dto.MessageDto;
import com.twozo.app.model.Dto.MessageResponseDto;
import com.twozo.app.model.Message;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDao {

	boolean addMessage(final Message message);

	boolean editMessage(final MessageDto messageDto);

	boolean deleteMessage(final int messageId);

	Collection<MessageResponseDto> getInboxMessages(final int userId);

	Collection<MessageResponseDto> getChatHistory(final MessageDto messageDto);
}
