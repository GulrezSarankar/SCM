package com.scm.Services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scm.Entity.Contact;
import com.scm.Entity.User;

public interface ContactService {

//	Save
	Contact save(Contact contact);
	
//	update 
	Contact update(Contact contact);
	
//	get contacts
	List<Contact> getall();
	
//	get contact by id
	Contact getByid(String id);
	
//	delete
	
	
	void delete(String id);
	
	//search
	 Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order,User user);

	    Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order,User user);

	    Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order,User user);
	 
	 
	 List<Contact> getByuserId(String userid);
	 Page<Contact> getByuser(User user, int page , int size,String sortBy,String direction);
}

