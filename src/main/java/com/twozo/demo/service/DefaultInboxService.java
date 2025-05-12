package com.twozo.demo.service;

import java.util.Collection;

import com.twozo.demo.dao.MessageDao;
import com.twozo.demo.model.Dto.MessageDto;
import com.twozo.demo.model.Dto.MessageResponseDto;
import org.springframework.stereotype.Service;

@Service
public class DefaultInboxService implements InboxService {

	private final MessageDao messageDao;
	
	public DefaultInboxService(MessageDao messageDao) {
		this.messageDao = messageDao;
	}
	
	public Collection<MessageResponseDto> getInboxHistory(int userId){
		
		return messageDao.getInboxMessages(userId);
	}
	
	public Collection<MessageResponseDto> getChatHistory(MessageDto messageDto){
		
		return messageDao.getChatHistory(messageDto);
		
	}
	

}
