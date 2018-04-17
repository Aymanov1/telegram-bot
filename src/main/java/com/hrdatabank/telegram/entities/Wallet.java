package com.hrdatabank.telegram.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames="idUser"))
public class Wallet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idWallet;
	private String idUser;
	private String walletCode;

	public long getIdWallet() {
		return idWallet;
	}

	public void setIdWallet(long idWallet) {
		this.idWallet = idWallet;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getWalletCode() {
		return walletCode;
	}

	public void setWalletCode(String walletCode) {
		this.walletCode = walletCode;
	}

	public Wallet() {
		super();
	}

	public Wallet(String idUser, String walletCode) {
		super();
		this.idUser = idUser;
		this.walletCode = walletCode;
	}

}
