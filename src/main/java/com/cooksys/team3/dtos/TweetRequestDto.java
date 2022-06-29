package com.cooksys.team3.dtos;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

public class TweetRequestDto {
	
	private String content;
	
	private CredentialsDto credentialsDto;
	
	

}
