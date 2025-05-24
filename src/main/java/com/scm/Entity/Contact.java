package com.scm.Entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Contact {
	
	@Id
	private String id;
	
	private String name;
	
	private String email;
	
	private String phoneNumber;
	
	private String address;
	
	private String picture;
	
	@Column(length = 1000)
	private String description;
	
	private boolean favorite=false;
	
	private String websiteLink;
	
	private String linkedLink;
	
//	private List<String> socialLinks= new ArrayList<>();
	
	private String cloudinaryImagePublicId;
	
	@ManyToOne
	@JsonIgnore
	private User user;
	
	@OneToMany(mappedBy = "contacts", cascade = CascadeType.ALL,fetch = FetchType.EAGER )
	private List<SocialLink> links=new ArrayList<>();
	
	



    

}
