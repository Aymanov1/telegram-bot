package com.hrdatabank.telegram.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hrdatabank.telegram.entities.LinkedUserTarget;
import com.hrdatabank.telegram.repositories.LinkedUserTargetRepository;

@Service
public class LinkedUserTargetService {
	@Autowired
	private LinkedUserTargetRepository linkedUserTargetRepository;

	public LinkedUserTargetRepository getLinkedUserTargetRepository() {
		return linkedUserTargetRepository;
	}

	public void setLinkedUserTargetRepository(LinkedUserTargetRepository linkedUserTargetRepository) {
		this.linkedUserTargetRepository = linkedUserTargetRepository;
	}

	public LinkedUserTarget saveLinkedUserTarget(LinkedUserTarget linkedUserTarget) {
		return linkedUserTargetRepository.save(linkedUserTarget);
	}

	public void deleteLinkedUserTarget(LinkedUserTarget linkedUserTarget) {
		linkedUserTargetRepository.delete(linkedUserTarget);
	}

	public void updateLinkedUserTarget(LinkedUserTarget linkedUserTarget) {
		linkedUserTargetRepository.setLinkedUserTargetById(linkedUserTarget.getTargetUser(),
				linkedUserTarget.getFromUser(), linkedUserTarget.isSentInvitation(),
				linkedUserTarget.getIdLinkedUserTarget());
	}

	public List<LinkedUserTarget> findLinkedUserTarget(String fromUser) {
		return linkedUserTargetRepository.find(fromUser);
	}
}
