package com.twozo.smapp.service;

import java.util.Collection;
import com.twozo.smapp.dao.MessageDao;
import com.twozo.smapp.dao.UserDao;
import com.twozo.smapp.model.InboxInfo;
import com.twozo.smapp.model.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

	private final UserDao userDao ;
	private final MessageDao messageDao;
	
	public MessageServiceImpl(final UserDao userDao, final  MessageDao messageDao) {
		this.userDao = userDao;
		this.messageDao = messageDao;
	}

	@Override
	public boolean send(final Message message) {
		return messageDao.add(message);
	}

	@Override
	public boolean edit(final Message message,String updateType) {
		return messageDao.update(message,updateType);
	}

	@Override
	public boolean delete(final Message message) {
		return messageDao.delete(message);
	}

	@Override
	public Collection<InboxInfo> getInbox(final int userId){
		return messageDao.getInbox(userId);
	}

	@Override
	public Collection<Message> getChatHistory(final int senderId,final int receiverId){
		messageDao.markMessagesAsSeen(senderId);
		return messageDao.getChatHistory(senderId,receiverId);
	}

	@Override
	public Collection<Message> getMessageReport(){
		return messageDao.getMessageReport();
	}
}
