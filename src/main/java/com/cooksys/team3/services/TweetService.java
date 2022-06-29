package com.cooksys.team3.services;

import org.springframework.http.ResponseEntity;

import com.cooksys.team3.dtos.TweetResponseDto;
import com.cooksys.team3.dtos.UserRequestDto;

public interface TweetService {

	ResponseEntity<TweetResponseDto> likeTweet(Long tweetId, UserRequestDto userRequestDto);
	
		
}
