package com.hrdatabank.telegram.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LinkedUserTarget implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idLinkedUserTarget;
	private String targetUser;
	private String fromUser;
	private boolean sentInvitation = false;

	public long getIdLinkedUserTarget() {
		return idLinkedUserTarget;
	}

	public void setIdLinkedUserTarget(long idLinkedUserTarget) {
		this.idLinkedUserTarget = idLinkedUserTarget;
	}

	public String getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(String targetUser) {
		this.targetUser = targetUser;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public LinkedUserTarget() {
		super();
	}

	public LinkedUserTarget(String targetUser, String fromUser) {
		super();
		this.targetUser = targetUser;
		this.fromUser = fromUser;
	}

	public boolean isSentInvitation() {
		return sentInvitation;
	}

	public void setSentInvitation(boolean sentInvitation) {
		this.sentInvitation = sentInvitation;
	}

}
