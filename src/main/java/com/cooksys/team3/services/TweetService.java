package com.cooksys.team3.services;

import java.util.List;

import com.cooksys.team3.dtos.TweetResponseDto;
import com.cooksys.team3.dtos.UserRequestDto;
import com.cooksys.team3.dtos.UserResponseDto;

public interface TweetService {

	List<TweetResponseDto> getAllTweets();
	
	TweetResponseDto getTweetById(Long id);
	
	List<UserResponseDto> getUsersWhoLikedTweet(Long id);
	
	List<TweetResponseDto> getReplies(Long id);
	
	List<TweetResponseDto> getReposts(Long id);

	void likeTweet(Long tweetId, UserRequestDto userRequestDto);

	TweetResponseDto repostTweet(Long id, UserRequestDto userRequestDto);

}
