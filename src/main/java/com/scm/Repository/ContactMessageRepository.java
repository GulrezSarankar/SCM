package com.scm.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scm.Entity.ContactMessage;

public interface ContactMessageRepository extends JpaRepository<ContactMessage,Long> {
	
    List<ContactMessage> findByReadStatusFalse();

}
