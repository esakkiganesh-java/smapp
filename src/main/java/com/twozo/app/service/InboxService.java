package com.twozo.app.service;

import java.util.Collection;
import com.twozo.app.model.dto.MessageResponseDto;
import com.twozo.app.model.dto.MessageDto;
import org.springframework.stereotype.Service;

@Service
public interface InboxService {

	Collection<MessageResponseDto> getInboxHistory(final int userId);

	Collection<MessageResponseDto> getChatHistory(final MessageDto messageDto);

}
