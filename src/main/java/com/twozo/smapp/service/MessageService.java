package com.twozo.smapp.service;

import com.twozo.smapp.model.InboxInfo;
import com.twozo.smapp.model.Message;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
public interface MessageService {

	void send(final Message message);

	void edit(final Message message, String updateType);

	void delete(final Message message);

	Collection<InboxInfo> getInbox(final int userId);

	Collection<Message> getChatHistory(final int senderId, final int receiverId);

    Collection<Message> getMessageReport();
}
