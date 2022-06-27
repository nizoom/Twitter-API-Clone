package com.cooksys.team3.dtos;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

public class TweetRequestDto {
	
	private int author;
	
	private Timestamp timestamp; 
	
	private boolean deleted;
	
	private String content;
	
	private int inReplyTo;
	
	private int repostOf;

}
