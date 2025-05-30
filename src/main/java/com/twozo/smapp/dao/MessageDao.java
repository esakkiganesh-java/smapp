package com.twozo.smapp.dao;

import java.util.Collection;
import com.twozo.smapp.model.InboxInfo;
import com.twozo.smapp.model.Message;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDao extends Dao<Message> {

	Collection<InboxInfo> getInbox(final int userId);

	Collection<Message> getChatHistory(final int senderId,final int receiverId);

	Collection<Message> getMessageReport();

	void markMessagesAsSeen(final int userId);

}
