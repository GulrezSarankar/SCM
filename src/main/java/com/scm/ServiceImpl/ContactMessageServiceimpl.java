package com.scm.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.scm.Entity.ContactMessage;
import com.scm.Entity.ContactMessageDto;
import com.scm.Repository.ContactMessageRepository;

@Service
public class ContactMessageServiceimpl implements ContactMessageService {
	
	@Autowired
    private  ContactMessageRepository repository;
	@Autowired
    private  JavaMailSender mailSender;

    @Override
    public ContactMessage saveMessage(ContactMessageDto dto) {
        ContactMessage message = new ContactMessage();
        message.setName(dto.getName());
        message.setEmail(dto.getEmail());
        message.setMessage(dto.getMessage());

        repository.save(message);

        // Notify admin
        sendEmailToAdmin(message);

        // Auto-reply to user
        sendAutoReplyToUser(message);

        return message;
    }

    @Override
    public List<ContactMessage> getAllMessages() {
        return repository.findAll();
    }

    @Override
    public List<ContactMessage> getUnreadMessages() {
        return repository.findByReadStatusFalse();
    }

    @Override
    public ContactMessage markAsRead(Long id) {
        ContactMessage msg = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        msg.setReadStatus(true);
        return repository.save(msg);
    }

    // Helper methods for email
    private void sendEmailToAdmin(ContactMessage message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("sarankargulrez127@gmail.com");
        mail.setSubject("New Contact Message from " + message.getName());
        mail.setText("Name: " + message.getName() + "\nEmail: " + message.getEmail() +
                "\nMessage: \n" + message.getMessage());
        mailSender.send(mail);
    }

    private void sendAutoReplyToUser(ContactMessage message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(message.getEmail());
        mail.setSubject("Thank you for contacting us!");
        mail.setText("Hi " + message.getName() + ",\n\n" +
                "Thank you for reaching out. We have received your message and will get back to you shortly.\n\n" +
                "Best regards,\nSmart Contacts Manager Team");
        mailSender.send(mail);
    }

	
}
