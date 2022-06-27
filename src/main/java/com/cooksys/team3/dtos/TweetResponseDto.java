package com.cooksys.team3.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@Data

public class TweetResponseDto {
	
	private int author;
	
	private Timestamp posted; 
	
	private boolean deleted;
	
	private String content;
	
	private int inReplyTo;
	
	private int repostOf;
	
	private List <TweetHashtags> tweetHashtags;
	
}
