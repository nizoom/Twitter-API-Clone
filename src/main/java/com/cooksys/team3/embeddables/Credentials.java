package com.cooksys.team3.embeddables;

import lombok.Data;

import javax.persistence.Column;

import javax.persistence.Embeddable;
@Data
@Embeddable

public class Credentials {

	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
}
