package com.twozo.app.service;

import java.util.Collection;
import com.twozo.app.dao.MessageDao;
import com.twozo.app.dao.UserDao;
import com.twozo.app.model.Dto.MessageDto;
import com.twozo.app.model.Dto.UserDto;
import com.twozo.app.model.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

	private final UserDao userDao ;
	private final MessageDao messageDao;
	
	public MessageServiceImpl(final UserDao userDao, final  MessageDao messageDao) {
		this.userDao = userDao;
		this.messageDao = messageDao;
	}

	public boolean sendMessage(final Message message) {

		final Collection<UserDto> allUsers = userDao.getAllUser();
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

	public boolean editMessage(final MessageDto messageDto) {

		return messageDao.editMessage(messageDto);
	}

	public boolean deleteMessage(final int messageId) {

		return messageDao.deleteMessage(messageId);
	}

}
