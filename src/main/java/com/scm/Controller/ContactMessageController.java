package com.scm.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.Entity.ContactMessage;
import com.scm.Entity.ContactMessageDto;
import com.scm.ServiceImpl.ContactMessageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") 
public class ContactMessageController  {
	
	
    private final ContactMessageService service;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@Valid @RequestBody ContactMessageDto dto) {
        ContactMessage saved = service.saveMessage(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContactMessage>> getAllMessages() {
        return ResponseEntity.ok(service.getAllMessages());
    }

    @GetMapping("/unread")
    public ResponseEntity<List<ContactMessage>> getUnreadMessages() {
        return ResponseEntity.ok(service.getUnreadMessages());
    }

    @PatchMapping("/read/{id}")
    public ResponseEntity<ContactMessage> markAsRead(@PathVariable Long id) {
        ContactMessage msg = service.markAsRead(id);
        return ResponseEntity.ok(msg);
    }

	
	
	


}
