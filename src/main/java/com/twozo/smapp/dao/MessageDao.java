package com.twozo.smapp.dao;

import java.util.Collection;

import com.twozo.smapp.model.Message;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDao extends Dao<Message> {

	boolean add(final Message message);

	boolean update(final Message message,final int updateType);

	boolean delete(final Message message);

	Collection<Message> getInbox(final int userId);

	Collection<Message> getChatHistory(final Message message);

	void markMessagesAsSeen(final int userId);

}
