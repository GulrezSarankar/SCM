package com.scm.Forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class ContactForms {
	
	

	
	
    private String name;

    private String email;

    private String phoneNumber;

    private String address;
	private String description;

	private boolean favorite=false;

	  
	private String websiteLink;

	
	private String linkedLink;

	
//	@ValidFile(message = "Invalid File")
	private MultipartFile  contactImage;

	
	private String picture;

}
