package com.twozo.smapp.service;

import java.util.Collection;
import com.twozo.smapp.dao.MessageDao;
import com.twozo.smapp.dao.UserDao;
import com.twozo.smapp.model.Message;
import com.twozo.smapp.model.User;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

	private final UserDao userDao ;
	private final MessageDao messageDao;
	
	public MessageServiceImpl(final UserDao userDao, final  MessageDao messageDao) {
		this.userDao = userDao;
		this.messageDao = messageDao;
	}

	public boolean send(final Message message) {

		final Collection<User> allUsers = userDao.getAllUser();
		boolean senderExists = false;
		boolean receiverExists = false;

		for (User userData : allUsers) {

			if (userData.getId() == message.getSenderId()) {
				senderExists = true;
			}

			if (userData.getId() == message.getReceiverId()) {
				receiverExists = true;
			}

		}

		if (!senderExists || !receiverExists) {
			return false;
		}

		return messageDao.add(message);
	}

	public boolean edit(final Message message,int updateType) {

		return messageDao.update(message,updateType);
	}

	public boolean delete(final Message message) {

		return messageDao.delete(message);
	}

	public Collection<Message> getInbox(final int userId){
        messageDao.markMessagesAsSeen(userId);
		return messageDao.getInbox(userId);
	}

	public Collection<Message> getChatHistory(final Message message){
		messageDao.markMessagesAsSeen(message.getSenderId());
		return messageDao.getChatHistory(message);
	}
}
