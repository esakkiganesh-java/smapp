package com.twozo.app.service;

import java.util.Collection;
import com.twozo.app.dao.MessageDao;
import com.twozo.app.model.Dto.MessageDto;
import com.twozo.app.model.Dto.MessageResponseDto;
import org.springframework.stereotype.Service;

@Service
public class InboxServiceImpl implements InboxService {

	private final MessageDao messageDao;
	
	public InboxServiceImpl(final MessageDao messageDao) {
		this.messageDao = messageDao;
	}
	
	public Collection<MessageResponseDto> getInboxHistory(final int userId){
		
		return messageDao.getInboxMessages(userId);
	}
	
	public Collection<MessageResponseDto> getChatHistory(final MessageDto messageDto){
		
		return messageDao.getChatHistory(messageDto);
		
	}
	

}
