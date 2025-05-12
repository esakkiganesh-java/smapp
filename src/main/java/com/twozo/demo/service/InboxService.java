package com.twozo.demo.service;

import java.util.Collection;


import com.twozo.demo.model.Dto.MessageResponseDto;
import com.twozo.demo.model.Dto.MessageDto;
import org.springframework.stereotype.Service;

@Service
public interface InboxService {

	Collection<MessageResponseDto> getInboxHistory(int userId);

	Collection<MessageResponseDto> getChatHistory(MessageDto messageDto);


}
