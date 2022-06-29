package com.cooksys.team3.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.team3.dtos.TweetResponseDto;
import com.cooksys.team3.dtos.UserRequestDto;
import com.cooksys.team3.dtos.UserResponseDto;
import com.cooksys.team3.entities.Tweet;
import com.cooksys.team3.entities.User;
import com.cooksys.team3.exceptions.BadRequestException;
import com.cooksys.team3.exceptions.NotFoundException;
import com.cooksys.team3.mappers.TweetMapper;
import com.cooksys.team3.mappers.UserMapper;
import com.cooksys.team3.repositories.TweetRepository;
import com.cooksys.team3.repositories.UserRepository;
import com.cooksys.team3.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserMapper userMapper;
	private final UserRepository userRepository;
	private final TweetMapper tweetMapper;
	private final TweetRepository tweetRepository;

	private void validateUser(UserRequestDto userRequestDto) {
		if (userRequestDto.getCredentialsDto() == null) {
			throw new BadRequestException("Please enter username and password");
		}
		if (userRequestDto.getCredentialsDto().getUsername() == null) {
			throw new BadRequestException("Please enter a username");
		}
		if (userRequestDto.getCredentialsDto().getPassword() == null) {
			throw new BadRequestException("Please enter a password");
		}
	}

	@Override
	public List<UserResponseDto> getUsers() {
		return userMapper.entityToDto(userRepository.findAllByDeletedFalse());
	}

	@Override
	public UserResponseDto updateUsername(String username, UserRequestDto userRequestDto) {
		validateUser(userRequestDto);

		return null;
	}

	@Override
	public List<TweetResponseDto> getFeed(String username) {
		return null;
	}

	@Override
	public List<TweetResponseDto> getTweets(String username) {
		Optional<User> optional = userRepository.findByCredentialsUsername(username);

		if (optional.isEmpty()) {
			throw new NotFoundException("Specified user could not be found");
		}
		List<Tweet> tweets = tweetRepository.findByAuthor(optional.get());
		return tweetMapper.entitiesToDtos(tweets);
	}

	@Override
	public List<UserResponseDto> getFollowers(String username) {
		return null;
	}

	@Override
	public List<TweetResponseDto> getMentions(String username) {
		Optional<User> requestedUser = userRepository.findByDeletedFalseAndCredentialsUsername(username);

		if (requestedUser.isEmpty()) {
			throw new NotFoundException("Specified user could not be found");
		}

		User user = requestedUser.get();
		List<Tweet> response = new ArrayList<>();
		List<Tweet> mentions = user.getMentions();
		for(Tweet mention : mentions) {
			if(!mention.isDeleted()) {
				response.add(mention);
			}
		}
		response.sort(Comparator.comparing(Tweet::getPosted).reversed());
		return tweetMapper.entitiesToDtos(response);
	}

	@Override
	public List<UserResponseDto> getFollowing(String username) {
		Optional<User> requestedUser = userRepository.findByDeletedFalseAndCredentialsUsername(username);
		
		if(requestedUser.isEmpty()) {
			throw new NotFoundException("Specified user could not be found");
		}
		User user = requestedUser.get();
		
		List<User> response = new ArrayList<>();
		List<User> following = user.getFollowing();
		for(User followedUser : following) {
			if(!followedUser.isDeleted()) {
				response.add(followedUser);
			}
		}
		
		return userMapper.entityToDto(response);
	}
}
