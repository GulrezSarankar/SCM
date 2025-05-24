package com.scm.Forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserForms {
	
	
	 
	    
	    @NotEmpty(message = "Username is required")

	    @Size(min = 3, message = "Min 3 Characters is required")
	    private String name;

	    @Email(message = "Invalid Email Address")
	    
	    @NotEmpty(message = "Email is required")
	    private String email;
	    
	    @NotEmpty(message = "Password is required")
	    @Size(min = 6, message = "Min 6 Characters is required")
	    private String password;

	    @NotEmpty(message = "About is required")
	    private String about;
	    
	    @Size(min = 8, max = 12, message = "Invalid Phone Number")
	    private String phoneNumber;
	
	
	
	 
	

	
	
	 

}
