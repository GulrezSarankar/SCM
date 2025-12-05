package com.scm.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.scm.Services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
	
	 @Autowired
	    private JavaMailSender eMailSender;

	    @Override
	    public void sendEmail(String to, String subject, String body) {

	        SimpleMailMessage message = new SimpleMailMessage();

	        message.setTo(to);
	        message.setSubject(subject);
	        message.setText(body);

	        // Do NOT set from for Mailtrap — it auto-assigns
	        // message.setFrom("your email");

	        eMailSender.send(message);
	    }

	    @Override
	    public void sendEmailWithHtml() {}

	    @Override
	    public void sendEmailWithAttachment() {}

}


