package com.cooksys.team3.dtos;

import java.sql.Timestamp;
import java.util.List;

import com.cooksys.team3.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

public class UserRequestDto {
	
	private CredentialsDto credentialsDto;
	
	private ProfileDto profileDto;	
	
}
