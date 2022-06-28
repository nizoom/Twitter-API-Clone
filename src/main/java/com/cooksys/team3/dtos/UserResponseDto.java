package com.cooksys.team3.dtos;

import java.sql.Timestamp;
import java.util.List;

import com.cooksys.team3.entities.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

public class UserResponseDto {

	private String username;
	
	private String password;
	
	private Timestamp joined;
	
	private boolean deleted;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String phone; 
	
	private List<User> followers;
	
	private List<User> following;
	
	private List<Tweet> tweets;
	
	
}
