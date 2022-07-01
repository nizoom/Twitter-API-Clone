package com.cooksys.team3.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.team3.dtos.ContextDto;
import com.cooksys.team3.dtos.CredentialsDto;
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
import com.cooksys.team3.services.TweetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

	private final TweetMapper tweetMapper;
	private final TweetRepository tweetRepository;
	private final UserMapper userMapper;
	private final UserRepository userRepository;

	// -------------------- HELPER METHODS --------------------
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

		if (tweet.get().isDeleted()) {
			throw new NotFoundException("Tweet with id " + tweetId + " is deleted. Please enter a different tweet id");
		}

		return tweet;

	}

	// -------------------- GET METHODS --------------------
	@Override
	public List<TweetResponseDto> getAllTweets() {
		return tweetMapper.entitiesToDtos(tweetRepository.findAllByDeletedFalse());
	}

	@Override
	public TweetResponseDto getTweetById(Long id) {
		Tweet tweet = validateTweet(id).get();

		return tweetMapper.entityToDto(tweet);
	}

	@Override
	public List<UserResponseDto> getUsersWhoLikedTweet(Long id) {
		Tweet tweet = validateTweet(id).get();

		return userMapper.entityToDto(tweet.getUserLikes());
	}

	@Override
	public List<TweetResponseDto> getReplies(Long tweetId) {

		Optional<Tweet> validatedTweet = validateTweet(tweetId);

		List<Tweet> replyChain = new ArrayList<>();

		for (Tweet tweet : validatedTweet.get().getReplyTweets()) {
			if (!tweet.isDeleted()) {
				replyChain.add(tweet);
			}
		}

		return tweetMapper.entitiesToDtos(replyChain);

	}

	@Override
	public List<TweetResponseDto> getReposts(Long id) {
		List<Tweet> allRepostTweets = validateTweet(id).get().getRepostTweets();
		List<Tweet> nonDeletedRepostTweets = new ArrayList<>();

		for (Tweet tweet : allRepostTweets) {
			if (!tweet.isDeleted()) {
				nonDeletedRepostTweets.add(tweet);
			}
		}

		return tweetMapper.entitiesToDtos(nonDeletedRepostTweets);
	}

	@Override
	public ContextDto getContextOfTweet(Long id) {
		// ContextDto contain variables not in Tweet entity so create ContextDto here
		ContextDto contextDto = new ContextDto();
		
		Tweet tweet = validateTweet(id).get();
		
		// Set the current tweet
		contextDto.setTarget(tweetMapper.entityToDto(tweet));
		
		// Create and set chain of replies leading to this tweet
		List<TweetResponseDto> beforeTweets = new ArrayList<>();
		Tweet currentTweet = tweet;
		
		while (currentTweet.getInReplyTo() != null) {
			currentTweet = currentTweet.getInReplyTo();
			
			if (!currentTweet.isDeleted()) {
				beforeTweets.add(tweetMapper.entityToDto(currentTweet));
			}
		}
	
		contextDto.setBefore(beforeTweets);
		
		// Create and set chain of replies following this tweet
		// Each reply contains a list of replies
		// Every time a reply is checked, its replies are added to a list to check for their replies
		List<TweetResponseDto> afterTweets = new ArrayList<>();
		List<Tweet> tweetsToCheckForReplyList = new ArrayList<>();
		tweetsToCheckForReplyList.add(tweet);
		
		while (!tweetsToCheckForReplyList.isEmpty()) {
			Tweet checkTweet = tweetsToCheckForReplyList.get(0);
			
			for (Tweet replyTweet : checkTweet.getReplyTweets()) {
				if (!replyTweet.isDeleted()) {
					afterTweets.add(tweetMapper.entityToDto(replyTweet));
				}
				tweetsToCheckForReplyList.add(replyTweet);
			
			}
			tweetsToCheckForReplyList.remove(0);
		}
		
		contextDto.setAfter(afterTweets);
		
		return contextDto;
	}

	// -------------------- POST METHODS --------------------
	@Override
	public void likeTweet(Long tweetId, UserRequestDto userRequestDto) {
		Optional<User> validatedUser = validateUser(userRequestDto.getCredentialsDto());

		Optional<Tweet> validatedTweet = validateTweet(tweetId);

		List<User> usersWhoLikeThisTweet = validatedTweet.get().getUserLikes();

		usersWhoLikeThisTweet.add(validatedUser.get());

		tweetRepository.saveAndFlush(validatedTweet.get());

	}

	@Override
	public TweetResponseDto repostTweet(Long id, UserRequestDto userRequestDto) {
		Optional<User> validatedUser = validateUser(userRequestDto.getCredentialsDto());
		
		Optional<Tweet> validatedTweet = validateTweet(id);
		
		Tweet tweet = validatedTweet.get();
		
		tweet.setContent(null);
		tweet.setRepostOf(tweet);
		tweet.setAuthor(validatedUser.get());
		tweet.setPosted(new Timestamp(System.currentTimeMillis()));
		
		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));
	}

	@Override
	public List<UserResponseDto> getMentions(Long tweetId) {
	
		Optional<Tweet> validatedTweet = validateTweet(tweetId);
		
		List <User> allUserMentions = validatedTweet.get().getUserMentions();
		
		List <User> mentionedUndeletedUsers = new ArrayList<>();
		for(User user : allUserMentions) {
			if(!user.isDeleted()) {
				mentionedUndeletedUsers.add(user);
			}
		}
		
		return userMapper.entityToDto(mentionedUndeletedUsers);
	}

}
