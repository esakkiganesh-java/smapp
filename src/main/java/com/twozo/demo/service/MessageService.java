package com.twozo.demo.service;


import com.twozo.demo.model.Dto.MessageDto;
import com.twozo.demo.model.Message;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {

	boolean sendMessage(Message message);

	boolean editMessage(MessageDto messageDto);

	boolean deleteMessage(int messageId);
}
