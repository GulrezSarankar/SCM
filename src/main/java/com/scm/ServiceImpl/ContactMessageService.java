package com.scm.ServiceImpl;

import java.util.List;

import com.scm.Entity.ContactMessage;
import com.scm.Entity.ContactMessageDto;

public interface ContactMessageService {
	
    ContactMessage saveMessage(ContactMessageDto dto); // Save new contact

    List<ContactMessage> getAllMessages(); // Admin: get all messages

    List<ContactMessage> getUnreadMessages(); // Admin: get unread messages

    ContactMessage markAsRead(Long id); // Admin: mark message as read

}
