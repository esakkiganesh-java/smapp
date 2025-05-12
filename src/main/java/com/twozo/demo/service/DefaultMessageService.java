package com.twozo.demo.service;

import java.util.Collection;
import com.twozo.demo.dao.MessageDao;
import com.twozo.demo.dao.UserDao;
import com.twozo.demo.model.Dto.MessageDto;
import com.twozo.demo.model.Dto.UserDto;
import com.twozo.demo.model.Message;
import org.springframework.stereotype.Service;

@Service
public class DefaultMessageService implements MessageService {

	private final UserDao userDao ;
	private final MessageDao messageDao;
	
	public DefaultMessageService(UserDao userDao, MessageDao messageDao) {
		this.userDao = userDao;
		this.messageDao = messageDao;
	}

	public boolean sendMessage(Message message) {

		Collection<UserDto> allUsers = userDao.getAllUser();

		boolean senderExists = false;
		boolean receiverExists = false;

		for (UserDto userDto : allUsers) {
			if (userDto.getId() == message.getSenderId()) {
				senderExists = true;
			}
			if (userDto.getId() == message.getReceiverId()) {
				receiverExists = true;
			}
		}

		if (!senderExists || !receiverExists) {
			return false;
		}

		return messageDao.addMessage(message);
	}

	public boolean editMessage(MessageDto messageDto) {

		return messageDao.editMessage(messageDto);
	}

	public boolean deleteMessage(int messageId) {

		return messageDao.deleteMessage(messageId);
	}

}
