package com.scm.Configuration;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.Entity.User;
import com.scm.Entity.providers;
import com.scm.Helper.AppConstants;
import com.scm.Repository.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenicationSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
	UserRepo re;
	
	
//    Logger logger = LoggerFactory.getLogger(OAuthAuthenicationSuccessHandler.class);
	
	Logger logger=LoggerFactory.getLogger(OAuthAuthenicationSuccessHandler.class);


	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication) 
					throws IOException, ServletException {
		logger.info("OAuthAuthenicationSuccessHandler");
		
//		Identify The Provider
		var oauth2AuthenicationToken=(OAuth2AuthenticationToken)authentication;
        String authorizedClientRegistrationId = oauth2AuthenicationToken.getAuthorizedClientRegistrationId();

		logger.info(authorizedClientRegistrationId);
		
		var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
		oauthUser.getAttributes().forEach((key, value) -> {
            logger.info(key + " : " + value);
        });
		
		User user= new User();
		user.setUserId(UUID.randomUUID().toString());
		user.setRoleList(List.of(AppConstants.ROLE_USER));
		user.setEmailVerified(true);
		user.setEnabled(true);
		user.setPassword("dummy");
		
		
		if (authorizedClientRegistrationId.equalsIgnoreCase("google")) 
		{
			
//			google 
//			google attributes
			user.setEmail(oauthUser.getAttribute("email").toString());
            user.setProfilePic(oauthUser.getAttribute("picture").toString());
            user.setName(oauthUser.getAttribute("name").toString());
            user.setProviderUserId(oauthUser.getName());
            user.setProvider(providers.GOOGLE);
            user.setAbout("This account is created using google.");
			
			
		}
		else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
			

//			Github attributes
			String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString()
                    : oauthUser.getAttribute("login").toString() + "@gmail.com";
			 String picture = oauthUser.getAttribute("avatar_url").toString();
	            String name = oauthUser.getAttribute("login").toString();
	            String providerUserId = oauthUser.getName();
	           
	            
	            
	            user.setEmail(email);
	            user.setProfilePic(picture);
	            user.setName(name);
	            user.setProviderUserId(providerUserId);
	            user.setProvider(providers.GITHUB);
	            user.setAbout("This account is created using github.");
			
		}
		else {
			logger.info("OAuthAuthenicationSuccessHandler: Unknown provider");
		}
		
//		 DefaultOAuth2User user= (DefaultOAuth2User) authentication.getPrincipal();
//		 
//		 logger.info(user.getName());
//		 
//		 user.getAttributes().forEach((key, value) -> {
//	            logger.info("{} => {}",key,value);
//	            
//	        });
//		 logger.info(user.getAuthorities().toString());
		 
//		 Save Database
//		 String email=user.getAttribute("email").toString();
//		 String name=user.getAttribute("name").toString();
//		 String picture=user.getAttribute("picture").toString();
//		 
//		 
////		 Create a user and save in database
//		 User user1=new User();
//		 user1.setEmail(email);
//		 user1.setName(name);
//		 user1.setProfilePic(picture);
//		 user1.setPassword("password");
//		 user1.setUserId(UUID.randomUUID().toString());
//		 user1.setProvider(providers.GOOGLE);
//		 user1.setEnabled(true);
//		 
//		 user1.setEmailVerified(true);
//		 user1.setProviderUserId(user.getName());
//		 user1.setRoleList(List.of("ROLE_USER"));
//		 user1.setAbout("This Account created using google");
//		 
//		 
//		 User user2=re.findByEmail(email).orElse(null);
//		 if (user2==null) {
//			 re.save(user1);
//			 logger.info("user save"+email);
//			
//		}
//		 
//		\
	
		User user2 = re.findByEmail(user.getEmail()).orElse(null);
        if (user2 == null) {
            re.save(user);
            System.out.println("user saved:" + user.getEmail());
        }
		 
//		response.sendRedirect("/home");
		new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
		
		

		// TODO Auto-generated method stub
		
	}

}
