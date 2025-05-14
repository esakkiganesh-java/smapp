package com.twozo.app.service;

import java.util.Collection;
import com.twozo.app.model.Dto.MessageResponseDto;
import com.twozo.app.model.Dto.MessageDto;
import org.springframework.stereotype.Service;

@Service
public interface InboxService {

	Collection<MessageResponseDto> getInboxHistory(final int userId);

	Collection<MessageResponseDto> getChatHistory(final MessageDto messageDto);


}
