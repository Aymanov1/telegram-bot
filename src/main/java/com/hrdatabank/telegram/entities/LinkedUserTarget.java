package com.hrdatabank.telegram.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames="targetUser"))
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
	private Date invitationDate;

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

	public LinkedUserTarget(String targetUser, String fromUser, Date date) {
		super();
		this.targetUser = targetUser;
		this.fromUser = fromUser;
		this.invitationDate = date;
	}

	public boolean isSentInvitation() {
		return sentInvitation;
	}

	public void setSentInvitation(boolean sentInvitation) {
		this.sentInvitation = sentInvitation;
	}

	public Date getInvitationDate() {
		return invitationDate;
	}

	public void setInvitationDate(Date invitationDate) {
		this.invitationDate = invitationDate;
	}

}
