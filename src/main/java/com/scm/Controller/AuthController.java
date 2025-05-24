package com.scm.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.Entity.User;
import com.scm.Helper.Message;
import com.scm.Helper.messageType;
import com.scm.Repository.UserRepo;

import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UserRepo re;
//	Verify Email	
	
	@GetMapping("/verify-email")
	public String verifyEmail(
			@RequestParam("token") String token,
			HttpSession session) {
		User user = re.findByEmailToken(token).orElse(null);

        if (user != null) {
            // user fetch hua hai :: process karna hai

            if (user.getEmailToken().equals(token)) {
                user.setEmailVerified(true);
                user.setEnabled(true);
                re.save(user);

                session.setAttribute("message", Message.builder()
                		.type(messageType.green)
                		.content("Your Email Is Verified!! Now You Can Login")
                		.build());
                
                return "success_page";
            }
            
            session.setAttribute("message", Message.builder()
            		.type(messageType.red)
            		.content("Email Not Verified!! Token is Not associated with user")
            		.build());
            return "error_page";
            
            
	
}
        session.setAttribute("message", Message.builder()
                .type(messageType.red)
                .content("Email not verified ! Token is not associated with user .")
                .build());

        return "error_page";
        

	}
}
