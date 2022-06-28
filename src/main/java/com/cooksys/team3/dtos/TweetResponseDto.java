package com.cooksys.team3.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@Data

public class TweetResponseDto {
	
	private Timestamp posted; 
	
	private String content;
	
	private CredentialsDto credentialsDto;
	

	
}
