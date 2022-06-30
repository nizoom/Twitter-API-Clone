package com.cooksys.team3.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.team3.dtos.CredentialsDto;
import com.cooksys.team3.dtos.TweetResponseDto;
import com.cooksys.team3.dtos.UserRequestDto;
import com.cooksys.team3.entities.Tweet;
import com.cooksys.team3.entities.User;
import com.cooksys.team3.exceptions.BadRequestException;
import com.cooksys.team3.exceptions.NotFoundException;
import com.cooksys.team3.mappers.TweetMapper;
import com.cooksys.team3.repositories.TweetRepository;
import com.cooksys.team3.repositories.UserRepository;
import com.cooksys.team3.services.TweetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

	private final TweetMapper tweetMapper;
	private final TweetRepository tweetRepository;
	private final UserRepository userRepository;

	private Optional<User> validateUser(CredentialsDto credentialsDto) {

		String username = credentialsDto.getUsername();

		String pw = credentialsDto.getPassword();

		Optional<User> matchingUser = userRepository.findByDeletedFalseAndCredentialsUsername(username);

		if (matchingUser == null) {
			throw new NotFoundException("Specified user could not be found");
		}

		if (matchingUser.get().getCredentials().getPassword() != pw) {
			throw new NotFoundException("Password does not match this username");
		}

		return matchingUser;

	}

	private Optional<Tweet> validateTweet(Long tweetId) {
		if (tweetId == null) {
			throw new BadRequestException("Please enter a tweet id");
		}

		Optional<Tweet> tweet = tweetRepository.findById(tweetId);

		if (tweet.isEmpty()) {
			throw new NotFoundException("No tweet found with id: " + tweetId);
		}

		if (!tweet.get().isDeleted()) {
			throw new NotFoundException("Tweet with id " + tweetId + " is deleted. Please enter a different tweet id");
		}

		return tweet;

	}

	@Override
	public List<TweetResponseDto> getAllTweets() {
		return tweetMapper.entitiesToDtos(tweetRepository.findAllByDeletedFalse());
	}

	@Override
	public TweetResponseDto getTweetById(Long id) {
		Optional<Tweet> optionalTweet = tweetRepository.findByDeletedFalseAndId(id);

		if (optionalTweet.isEmpty()) {
			throw new NotFoundException("No tweet found with id: " + id);
		} else if (optionalTweet.get().isDeleted()) {
			throw new NotFoundException("The tweet with id " + id + " has been deleted");
		}

		return tweetMapper.entityToDto(optionalTweet);
	}

	@Override
	public void likeTweet(Long tweetId, UserRequestDto userRequestDto) {
		Optional<User> validatedUser = validateUser(userRequestDto.getCredentialsDto());

		Optional<Tweet> validatedTweet = validateTweet(tweetId);

		List<User> usersWhoLikeThisTweet = validatedTweet.get().getUserLikes();

		usersWhoLikeThisTweet.add(validatedUser.get());

		tweetRepository.saveAndFlush(validatedTweet.get());

	}

	@Override
	public List<TweetResponseDto> getReplies(Long tweetId) {
		
		Optional<Tweet> validatedTweet = validateTweet(tweetId);
		
		List <Tweet> replyChain = new ArrayList<>();
		
		for (Tweet tweet : validatedTweet.get().getReplyTweets()) {
			if(!tweet.isDeleted()) {
				replyChain.add(tweet);
			}
		}
		
		List<TweetResponseDto> replyChainToDto = tweetMapper.entitiesToDtos(replyChain);
		
		return replyChainToDto;
			
	}

}
