package com.cooksys.team3.dtos;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

public class TweetRequestDto {
	
	private int author;
	
	private Timestamp posted; 
	
	private boolean deleted;
	
	private String content;
	
	private int inReplyTo;
	
	private int repostOf;
	
	private List <TweetHashtags> tweetHashtags;

}
