package com.hrdatabank.telegram.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrdatabank.telegram.entities.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Serializable> {
	Wallet findByIdUser(String idUser);
}
