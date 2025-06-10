package com.twozo.smapp.service;

import java.util.Collection;
import com.twozo.smapp.dao.MessageDao;
import com.twozo.smapp.model.InboxInfo;
import com.twozo.smapp.model.Message;
import org.springframework.stereotype.Service;

@Service
class MessageServiceImpl implements MessageService {

	private final MessageDao messageDao;
	
	public MessageServiceImpl( final  MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	@Override
	public String send(final Message message) {
		return messageDao.add(message);
	}

	@Override
	public String edit(final Message message, final String updateType) {
		return messageDao.update(message, updateType);
	}

	@Override
	public String delete(final Message message) {
		return messageDao.delete(message);
	}

	@Override
	public Collection<InboxInfo> getInbox(final int userId){
		return messageDao.getInbox(userId);
	}

	@Override
	public Collection<Message> getChatHistory(final int senderId, final int receiverId){
		messageDao.markMessagesAsSeen(senderId);
		return messageDao.getChatHistory(senderId,receiverId);
	}

	@Override
	public Collection<Message> getMessageReport(){
		return messageDao.getMessageReport();
	}
}
