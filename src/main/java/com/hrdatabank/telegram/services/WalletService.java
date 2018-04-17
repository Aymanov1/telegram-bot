package com.hrdatabank.telegram.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hrdatabank.telegram.entities.Wallet;
import com.hrdatabank.telegram.repositories.WalletRepository;

@Service
public class WalletService {
	@Autowired
	private WalletRepository walletRepository;

	public WalletRepository getWalletRepository() {
		return walletRepository;
	}

	public void setWalletRepository(WalletRepository walletRepository) {
		this.walletRepository = walletRepository;
	}

	public Wallet saveWallet(Wallet wallet) {
		return walletRepository.save(wallet);
	}

	public void deleteWallet(Wallet wallet) {
		walletRepository.delete(wallet);
	}

	
	public Wallet findByIDUserWallet(String idUser) {
		return walletRepository.findByIdUser(idUser);
	}
}
