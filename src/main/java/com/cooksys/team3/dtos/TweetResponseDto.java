package com.cooksys.team3.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data

public class TweetResponseDto {
	
	private int author;
	
	private Timestamp timestamp; 
	
	private boolean deleted;
	
	private String content;
	
	private int inReplyTo;
	
	private int repostOf;
	
}
