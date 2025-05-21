package com.twozo.smapp.service;

import com.twozo.smapp.model.Message;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
public interface MessageService {

	boolean send(final Message message);

	boolean edit(final Message message,int updateType);

	boolean delete(final Message message);

	Collection<Message> getInbox(final int userId);

	Collection<Message> getChatHistory(final Message messageDto);

}
