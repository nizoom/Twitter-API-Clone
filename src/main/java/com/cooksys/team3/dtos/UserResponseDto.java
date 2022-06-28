package com.cooksys.team3.dtos;

import java.sql.Timestamp;
import java.util.List;

import com.cooksys.team3.embeddables.Credentials;
import com.cooksys.team3.embeddables.Profile;
import com.cooksys.team3.entities.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

public class UserResponseDto {
	
	private CredentialsDto credentialsDto;
	
	private ProfileDto profileDto;
	
	
}
