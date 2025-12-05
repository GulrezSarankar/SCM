package com.scm.Controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.Entity.User;
import com.scm.Helper.Helper;
import com.scm.Services.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
	
	
	
	@Autowired
	UserService sr;
	
	
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    
    
//    @ModelAttribute
//    public void addLoggedInUserInformation(Model model, Authentication authentication) {
//    	System.out.println("Adding Logged in User form the model");
//    	String username= Helper.getEmailOfLoggedInUser(authentication);
//    	String name=authentication.getName();
//    	logger.info("user logged in {}",username);
//    	
//    	
//    	
//    User user=	sr.getUserByEmail(username);
//    
//    System.out.println(user.getName());
//    System.out.println(user.getEmail());
//    model.addAttribute("loggedinuser",user);
//    	
//    }

	

	
//	User DashBoard
//
	@RequestMapping(value = "/dashboard")
    public String userDashboard() {
        System.out.println("User dashboard");
        return "user/dashboard";
    }

    // User Profile Page
    @RequestMapping(value = "/profile")
    public String userProfile(Model model, Authentication authentication) {
    	System.out.println("Profile Page");
    	
//    	String name= authentication.getName();
//        String name=	Helper.getEmailOfLoggedInUser(authentication);
//
//    	logger.info("user logged in: {}", name);
//    	
//       User user=	sr.getUserByEmail(name);
//       System.out.println(user.getName());
//       System.out.println(user.getEmail());
//       
//       model.addAttribute("loggedinuser", user);
//    
    
            return "user/profile";
    }
	
//	USer Add Contact Page 
	
	
//	User View Contact Page
	
	
//	user edit Page
	
//	User delete page 
}
