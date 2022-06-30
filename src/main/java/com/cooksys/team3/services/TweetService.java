package com.cooksys.team3.services;

import java.util.List;

import com.cooksys.team3.dtos.CredentialsDto;
import com.cooksys.team3.dtos.TweetResponseDto;
import com.cooksys.team3.dtos.UserRequestDto;

public interface TweetService {

	List<TweetResponseDto> getAllTweets();
	
	TweetResponseDto getTweetById(Long id);

	void likeTweet(Long tweetId, UserRequestDto userRequestDto);

}
