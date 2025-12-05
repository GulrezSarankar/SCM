package com.scm.ServiceImpl;

import java.util.List;
import java.util.UUID;

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.Entity.Contact;
import com.scm.Entity.User;
import com.scm.Helper.ResourceNotFoundException;
import com.scm.Repository.ContactRepo;
import com.scm.Services.ContactService;




@Service
public class ContactServiceImpl implements ContactService {

	@Autowired 
	ContactRepo re;
	
	@Override
    public Contact save(Contact contact1) {

		String contactid=UUID.randomUUID().toString();
		contact1.setId(contactid);
		return re.save(contact1);

    }

	@Override
	public Contact update(Contact contact) {
		 var contactOld = re.findById(contact.getId())
	                .orElseThrow(() -> new ResourceNotFoundException("Contact not found"));
	        contactOld.setName(contact.getName());
	        contactOld.setEmail(contact.getEmail());
	        contactOld.setPhoneNumber(contact.getPhoneNumber());
	        contactOld.setAddress(contact.getAddress());
	        contactOld.setDescription(contact.getDescription());
	        contactOld.setPicture(contact.getPicture());
	        contactOld.setFavorite(contact.isFavorite());
	        contactOld.setWebsiteLink(contact.getWebsiteLink());
	        contactOld.setLinkedLink(contact.getLinkedLink());
	        contactOld.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());
//	        contactOld.setLinks(contact.getLinks());

	        return re.save(contactOld);
	    }


	@Override
	public List<Contact> getall() {
		
		return re.findAll();
	}

	@Override
	public Contact getByid(String id) {
		return re.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id " + id));
    }

	@Override
	public void delete(String id) {
		var contact = re.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id " + id));
        re.delete(contact);

		
	}



	@Override
	public List<Contact> getByuserId(String userid) {
		
		return re.findByUserId(userid);
		 
	}

	@Override
	public Page<Contact> getByuser(User user, int page , int size,String sortBy,String order) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

	var pageable =PageRequest.of(page, size,sort);
		
		// TODO Auto-generated method stub
		return re.findByUser(user, pageable);
	}

	@Override
	public Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order,User user) {
		// TODO Auto-generated method stub
		
	
		 Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
	        var pageable = PageRequest.of(page, size, sort);
	        return re.findByUserAndNameContaining(user, nameKeyword, pageable);
	}

	@Override
	public Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order,User user) {
		Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return re.findByUserAndEmailContaining(user, emailKeyword, pageable);
	}

	@Override
	public Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy,
			String order,User user) {
		Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return re.findByUserAndPhoneNumberContaining( user, phoneNumberKeyword, pageable);
	}

}
