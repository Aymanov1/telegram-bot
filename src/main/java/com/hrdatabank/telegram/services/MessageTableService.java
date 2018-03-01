package com.hrdatabank.telegram.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hrdatabank.telegram.entities.MessageTable;
import com.hrdatabank.telegram.repositories.MessageTableRepository;

@Service
public class MessageTableService {
	@Autowired
	private MessageTableRepository messageTableRepository;

	public MessageTableRepository getMessageTableRepository() {
		return messageTableRepository;
	}

	public void setMessageTableRepository(MessageTableRepository messageTableRepository) {
		this.messageTableRepository = messageTableRepository;
	}
	
	
	public MessageTable saveMessageTable(MessageTable messageTable) {
		return messageTableRepository.save(messageTable);
	}
	
}
