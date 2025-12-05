package com.scm.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.scm.Entity.User;
import com.scm.Forms.UserForms;
import com.scm.Helper.Message;
import com.scm.Helper.messageType;
import com.scm.Services.UserService;
/*import com.scm.helpers.MessageType;*/

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller

public class PageController {
	
	@Autowired
	private UserService sr;
	
	
	@ModelAttribute("UserForm")
	public UserForms setupUserForms() {
		return new UserForms();
	}
	@GetMapping("/")
	public String index() {
		return "redirect:/home";
	}
	
	//home
	@RequestMapping("/home")
	public String home(Model model) {
		System.out.println("Home Page Handler");
		model.addAttribute("name", "substring");
		model.addAttribute("YoutubeChannel", "Code");
		
		return"home";
	}
	
	//About
	
	@GetMapping("/about")
	public String aboutpage(Model model) {
		model.addAttribute("isLogin", true);
		System.out.println("This is About page");
		return"about";
	}
	
	//Services 
	
	@GetMapping("/service")
	public String servicepage() {
		return "service";
	}
	
	//contact
	
		@GetMapping("/contact")
		public String ContactPage() {
			return "contact";
		}
		
		//login
		
		@GetMapping("/login")
		public String loginPage() {
			return "login";
		}
		//Post Login 
		@PostMapping("/login")
		public String login() {
			return "login";
		}
		
		
		//SignUp
		
		@GetMapping("/register")
		public String signup(Model model) {
			UserForms userForm = new UserForms();
////			userForm.setName("Gulrez");
			model.addAttribute("userForm", userForm);
			
			return "register";
		}
		
		
		
//		Processing Register
	    @PostMapping(value = "/do-register")
	    public String processRegister( @ModelAttribute UserForms userform, //BindingResult result,
	            HttpSession session) {
	        System.out.println("Processing registration");
	        
	        
	        // fetch form data
	        
	        
	        
	        
	        
	        // UserForm
	        System.out.println(userform);
//	       
	        

//	         validate form data
//	        if (result.hasErrors()) {
//	        	return"register";
//				
//			}
	       
//	        
	        
	        // TODO::Validate userForm[Next Video]

	        // save to database

	        // userservice

	        // UserForm--> User
	        // User user = User.builder()
	        // .name(userForm.getName())
	        // .email(userForm.getEmail())
	        // .password(userForm.getPassword())
	        // .about(userForm.getAbout())
	        // .phoneNumber(userForm.getPhoneNumber())
	        // .profilePic(
	        // "https://www.learncodewithdurgesh.com/_next/image?url=%2F_next%2Fstatic%2Fmedia%2Fdurgesh_sir.35c6cb78.webp&w=1920&q=75")
	        // .build();

	        User user = new User();
	        user.setName(userform.getName());
	        user.setEmail(userform.getEmail());
	        user.setPassword(userform.getPassword());
	        user.setAbout(userform.getAbout());
	        user.setPhoneNumber(userform.getPhoneNumber());
	        user.setEnabled(false);
	        user.setProfilePic(
	                "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.instagram.com%2Fgulrez_sarankar18%2F&psig=AOvVaw1HeTUK5MzlQTQpxZMzjihI&ust=1744011291139000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCLCk-b7ywowDFQAAAAAdAAAAABAE");

	        User savedUser = sr.saveUser(user);

	        System.out.println("user saved :");

	        // message = "Registration Successful"

	        // add the message:

	        Message message = Message.builder().content("Registration Successful").type(messageType.blue
	        		).build();

	        session.setAttribute("message",message);

	        // redirect to login page
	        return "redirect:/register";
	    }
	
	
	
	

}
