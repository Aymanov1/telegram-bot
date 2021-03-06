package com.hrdatabank.telegram.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrdatabank.telegram.entities.MessageTable;

@Repository
public interface MessageTableRepository extends JpaRepository<MessageTable, Serializable> {

	List<String> findByFromUser(String fromUser);

}