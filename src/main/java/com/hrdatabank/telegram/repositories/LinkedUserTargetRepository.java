package com.hrdatabank.telegram.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hrdatabank.telegram.entities.LinkedUserTarget;

@Repository
public interface LinkedUserTargetRepository extends JpaRepository<LinkedUserTarget, Serializable> {
	@Query("SELECT l FROM LinkedUserTarget l WHERE LOWER(l.targetUser) = LOWER(:targetUser) and l.sentInvitation = false")
	public List<LinkedUserTarget> find(@Param("targetUser") String targetUser);
	
	@Query("SELECT count(l) FROM LinkedUserTarget l WHERE LOWER(l.fromUser) = LOWER(:fromUser) and l.sentInvitation = true")
	public int count(@Param("fromUser") String fromUser);

	@Modifying
	@Query("update LinkedUserTarget u set u.targetUser = ?1, u.fromUser = ?2, u.sentInvitation = ?3 where u.idLinkedUserTarget = ?4")
	void setLinkedUserTargetById(String targetUser, String fromUser, boolean sentInvitation, long idLinkedUserTarget);
}
