package com.scm.Controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.Entity.Contact;
import com.scm.Entity.User;
import com.scm.Forms.ContactForms;
import com.scm.Forms.ContactSearchForm;
import com.scm.Helper.AppConstants;
import com.scm.Helper.Helper;
import com.scm.Helper.Message;
import com.scm.Helper.messageType;
import com.scm.Services.ContactService;
import com.scm.Services.ImageService;
import com.scm.Services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/user/contacts")
public class ContactController {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);
	
	@Autowired
	private UserService userService;
  
	@Autowired
	private ContactService sr;
	
	@Autowired
	private ImageService imageService;


	@RequestMapping("/add")
	public String addContactView(Model model) {
		ContactForms contactForms= new ContactForms();
//		contactForms.setName("Gulrez Sarankar");
		contactForms.setFavorite(true);
		model.addAttribute("contactForms", contactForms);
		
		
		return "user/add_contact";
		
	}
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveForm(@Valid @ModelAttribute ContactForms contactForms ,BindingResult result ,
			Authentication authentication,HttpSession session) {
		
		if (result.hasErrors()) {
			session.setAttribute("message",Message.builder()
					.content("Please Correct The Following Errors")
					.type(messageType.red)
					.build());
			
		}
		
		
		
		
		
		String username=Helper.getEmailOfLoggedInUser(authentication);
		User user=userService.getUserByEmail(username);
		
//		Process The Contact Picture
//		logger.info("file information {}",contactForms.getContactImage().getOriginalFilename());
//		Upload file
		String filename=UUID.randomUUID().toString();

//		Process The Url
		String fileURL=imageService.uploadImage(contactForms.getContactImage(),filename);
		
		Contact contact=new Contact();
		
		
//		Contact contact = new Contact();
        contact.setName(contactForms.getName());
        contact.setFavorite(contactForms.isFavorite());
        contact.setEmail(contactForms.getEmail());
        contact.setPhoneNumber(contactForms.getPhoneNumber());
        contact.setAddress(contactForms.getAddress());
        contact.setDescription(contactForms.getDescription());
        contact.setUser(user);
        contact.setLinkedLink(contactForms.getLinkedLink());
        contact.setWebsiteLink(contactForms.getWebsiteLink());
        contact.setPicture(fileURL);
        contact.setCloudinaryImagePublicId(filename);
        
		
		sr.save(contact);
//		Set The Contact Picture URL
		System.out.println(contactForms);
		
		session.setAttribute("message", Message.builder().content("You Have Successfully Added A New Contact")
				.type(messageType.green)
				.build());
		
		return"redirect:/user/contacts/add";
	}
	
//	View Contact 
	
	@RequestMapping
	public String Viewcontacts(
			@RequestParam(value = "page",defaultValue = "0") int page,
			@RequestParam(value = "size",defaultValue = AppConstants.PAGE_SIZE+"") int size,
			@RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
			@RequestParam(value = "direction",defaultValue = "asc") String direction
			,Model model, Authentication authentication) {
//		Load All The User
		String username= Helper.getEmailOfLoggedInUser(authentication);
		
        User user = userService.getUserByEmail(username);
        Page<Contact>pageContact=	sr.getByuser(user,page,size,sortBy,direction);
       
        model.addAttribute("pageContact",pageContact);
        model.addAttribute("pageSize",AppConstants.PAGE_SIZE);
        
        model.addAttribute("contactSearchForm", new ContactSearchForm());
		
		  
		return"user/contacts";
		
	}
	
	//Search Handler
	@RequestMapping("/Search")
	public String Search(
			@ModelAttribute ContactSearchForm contactSearchForm,
			@RequestParam(value = "size",defaultValue = AppConstants.PAGE_SIZE+"")int size,
			@RequestParam(value = "page",defaultValue = "0") int page,
			@RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
			@RequestParam(value = "direction",defaultValue = "asc") String direction, 
			Model model,Authentication authentication
			) {
		
		logger.info("field {}  keyword {}", contactSearchForm.getField(),contactSearchForm.getValue() );
		 var user= userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));
		
		Page<Contact> pageContact=null;
		  
		if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContact = sr.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContact = sr.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
            pageContact = sr.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy,
                    direction, user);
        }
		
		logger.info("pageContact {}",pageContact);
		model.addAttribute("pageContact",pageContact);
		
		model.addAttribute("pageSize",AppConstants.PAGE_SIZE);   
		
		model.addAttribute("contactSearchForm",contactSearchForm);
		return"user/Search";
	}
	
    // detete contact
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(
            @PathVariable("contactId") String contactId,HttpSession session) {
        sr.delete(contactId);
        logger.info("contactId {} deleted", contactId);
        
        
        session.setAttribute("message",
                Message.builder()
                        .content("Contact is Deleted successfully !! ")
                        .type(messageType.green)
                        .build()

        );
        return "redirect:/user/contacts";

   
}
    
    @GetMapping("/view/{contactId}")
    public String updateContactFormView(
            @PathVariable("contactId") String contactId,
            Model model) {

      var contact = sr.getByid(contactId);
        ContactForms contactForms = new ContactForms();
        contactForms.setName(contact.getName());
        contactForms.setEmail(contact.getEmail());
        contactForms.setPhoneNumber(contact.getPhoneNumber());
        contactForms.setAddress(contact.getAddress());
        contactForms.setDescription(contact.getDescription());
        contactForms.setFavorite(contact.isFavorite());
        contactForms.setWebsiteLink(contact.getWebsiteLink());
        contactForms.setLinkedLink(contact.getLinkedLink());
        contactForms.setPicture(contact.getPicture());
        
        model.addAttribute("contactForms", contactForms);
        model.addAttribute("contactId", contactId);

        return "user/update_contact_view";
    }
    
    
   
    @RequestMapping(value = "/update/{contactId}",method = RequestMethod.POST)
    public String updateContact(@PathVariable String contactId,@ModelAttribute  ContactForms contactForms, Model model ) {
    	
    	 var con = sr.getByid(contactId);
         con.setId(contactId);
         con.setName(contactForms.getName());
         con.setEmail(contactForms.getEmail());
         con.setPhoneNumber(contactForms.getPhoneNumber());
         con.setAddress(contactForms.getAddress());
         con.setDescription(contactForms.getDescription());
         con.setFavorite(contactForms.isFavorite());
         con.setWebsiteLink(contactForms.getWebsiteLink());
         con.setLinkedLink(contactForms.getLinkedLink());
         
         if (contactForms.getContactImage() != null && !contactForms.getContactImage().isEmpty()) {
             logger.info("file is not empty");
             String fileName = UUID.randomUUID().toString();
             String imageUrl = imageService.uploadImage(contactForms.getContactImage(), fileName);
             con.setCloudinaryImagePublicId(fileName);
             con.setPicture(imageUrl);
             contactForms.setPicture(imageUrl);

         } else {
             logger.info("file is empty");
         }
         
         
         var updateCon = sr.update(con);
         logger.info("updated contact {}", updateCon);

         model.addAttribute("message", Message.builder().content("Contact Updated !!").type(messageType.green).build());
     
    	return "redirect:/user/contacts/view/"+contactId;
    }
    
    
    }