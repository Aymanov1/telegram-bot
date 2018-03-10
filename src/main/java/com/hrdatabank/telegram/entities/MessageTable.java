package com.hrdatabank.telegram.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MessageTable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idMessage;
	private String messageIdTelegram;
	private String fromUser;
	private String date;
	@Column(length = 1100)
	private String messageText;

	public MessageTable() {
		super();
	}


	public MessageTable(String messageIdTelegram, String fromUser, String date, String messageText) {
		super();
		this.messageIdTelegram = messageIdTelegram;
		this.fromUser = fromUser;
		this.date = date;
		this.messageText = messageText;

	}

	public long getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(long idMessage) {
		this.idMessage = idMessage;
	}

	public String getMessageIdTelegram() {
		return messageIdTelegram;
	}

	public void setMessageIdTelegram(String messageIdTelegram) {
		this.messageIdTelegram = messageIdTelegram;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}


}
