package com.cooksys.team3.services.impl;

import com.cooksys.team3.dtos.TweetResponseDto;
import com.cooksys.team3.dtos.UserRequestDto;
import com.cooksys.team3.dtos.UserResponseDto;
import com.cooksys.team3.entities.User;
import com.cooksys.team3.mappers.TweetMapper;
import com.cooksys.team3.repositories.TweetRepository;
import com.cooksys.team3.repositories.UserRepository;
import com.cooksys.team3.services.TweetService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class TweetServiceImpl implements TweetService {
	
	private final TweetMapper tweetMapper;
	private final TweetRepository tweetRepository;
	private final UserRepository userRepository;
	



	@Override

	public ResponseEntity<TweetResponseDto> likeTweet(Long tweetId, UserRequestDto userRequestDto) {
	
	
		return null;

	
	}
	
}
