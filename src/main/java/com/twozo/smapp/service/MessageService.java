package com.twozo.smapp.service;

import com.twozo.smapp.model.InboxInfo;
import com.twozo.smapp.model.Message;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
public interface MessageService {

	String send(final Message message);

	String edit(final Message message, String updateType);

	String delete(final Message message);

	Collection<InboxInfo> getInbox(final int userId);

	Collection<Message> getChatHistory(final int senderId, final int receiverId);

    Collection<Message> getMessageReport();
}
