package com.twozo.smapp.service;

import com.twozo.smapp.model.InboxInfo;
import com.twozo.smapp.model.Message;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
public interface MessageService {

	boolean send(final Message message);

	boolean edit(final Message message,String updateType);

	boolean delete(final Message message);

	Collection<InboxInfo> getInbox(final int userId);

	Collection<Message> getChatHistory(final int senderId,final int receiverId);

    Collection<Message> getMessageReport();
}
