package com.hrdatabank.telegram.repositories;

import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import com.hrdatabank.telegram.entities.MessageTable;

public class MessageTableRepositoryImpl implements MessageTableRepositoryCustom {
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void refresh(MessageTable messageTable) {
		em.refresh(messageTable);
	}
}