package com.cooksys.team3.embeddables;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Profile {

	private String firstName;
	
	private String lastName;
	
	@Column(nullable = false)
	private String email;
	
	private String phone;
}
