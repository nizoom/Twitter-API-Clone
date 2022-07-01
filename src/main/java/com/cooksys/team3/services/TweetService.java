package com.cooksys.team3.services;

import java.util.List;

import com.cooksys.team3.dtos.ContextDto;
import com.cooksys.team3.dtos.CredentialsDto;
import com.cooksys.team3.dtos.HashtagDto;
import com.cooksys.team3.dtos.TweetRequestDto;
import com.cooksys.team3.dtos.TweetResponseDto;
import com.cooksys.team3.dtos.UserRequestDto;
import com.cooksys.team3.dtos.UserResponseDto;

public interface TweetService {

	 

	List<TweetResponseDto> getAllTweets();

	TweetResponseDto getTweetById(Long id);

	List<UserResponseDto> getUsersWhoLikedTweet(Long id);

	List<TweetResponseDto> getReplies(Long id);

	List<TweetResponseDto> getReposts(Long id);

	ContextDto getContextOfTweet(Long id);
	
	List<UserResponseDto> getMentions(Long id);

	List<HashtagDto> getTags(Long id);

	TweetResponseDto createTweet(String content, CredentialsDto credentialsDto);

	void likeTweet(Long tweetId, UserRequestDto userRequestDto);

	TweetResponseDto repostTweet(Long id, UserRequestDto userRequestDto);

	TweetResponseDto replyTweet(Long id, TweetRequestDto tweetRequestDto);

	TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto);
}
