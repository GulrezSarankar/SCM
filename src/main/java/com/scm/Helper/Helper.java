package com.scm.Helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class Helper {
	
	public static String getEmailOfLoggedInUser(Authentication authentication) {

        // Check if user logged in via OAuth2
        if (authentication instanceof OAuth2AuthenticationToken aOAuth2AuthenticationToken) {

            String clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            OAuth2User oauth2User = aOAuth2AuthenticationToken.getPrincipal(); // ✅ Correct way to get principal

            String username = "";

            switch (clientId.toLowerCase()) {
                case "google" -> {
                    System.out.println("Getting email from Google");
                    username = oauth2User.getAttribute("email");
                }
                case "github" -> {
                    System.out.println("Getting email from GitHub");
                    username = oauth2User.getAttribute("email") != null 
                            ? oauth2User.getAttribute("email") 
                            : oauth2User.getAttribute("login") + "@github.com"; // Use GitHub domain
                }
                default -> {
                    System.out.println("OAuth2 provider not supported: " + clientId);
                }
            }

            return username;

        } else {
            // Local database login
            System.out.println("Getting data from local database");
            return authentication.getName();
        }
    }

	
	public String getLinkForEmailVerificatiton(String emailToken) {
    	
    	String Link="http://localhost:9081/auth/verify-email?token="+ emailToken;

//        return this.baseUrl + "/auth/verify-email?token=" + emailToken;
    	return Link;

    }

}
