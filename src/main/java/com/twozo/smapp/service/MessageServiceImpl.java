package com.twozo.smapp.service;

import java.util.Collection;
import com.twozo.smapp.dao.MessageDao;
import com.twozo.smapp.dao.UserDao;
import com.twozo.smapp.model.dto.MessageDto;
import com.twozo.smapp.model.dto.MessageResponseDto;
import com.twozo.smapp.model.dto.UserDto;
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

		return messageDao.add(message);
	}

	public boolean editMessage(final MessageDto messageDto,int updateType) {

		return messageDao.update(messageDto,updateType);
	}

	public boolean deleteMessage(final MessageDto messageDto) {

		return messageDao.delete(messageDto);
	}

	public Collection<MessageResponseDto> getInboxHistory(final int userId){
        messageDao.markMessagesAsSeen(userId);
		return messageDao.getInboxMessages(userId);
	}

	public Collection<MessageResponseDto> getChatHistory(final MessageDto messageDto){
		messageDao.markMessagesAsSeen(messageDto.getSenderId());
		return messageDao.getChatHistory(messageDto);
	}
}
