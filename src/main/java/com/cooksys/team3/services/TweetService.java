package com.cooksys.team3.services;

import java.util.List;

import com.cooksys.team3.dtos.CredentialsDto;
import com.cooksys.team3.dtos.TweetResponseDto;

public interface TweetService {

	List<TweetResponseDto> getAllTweets();

	void likeTweet(Long tweetId, CredentialsDto credentialsDto);

}
