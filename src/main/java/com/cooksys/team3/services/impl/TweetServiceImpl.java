package com.cooksys.team3.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.team3.dtos.CredentialsDto;
import com.cooksys.team3.dtos.TweetResponseDto;
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

	private User validateUser(CredentialsDto credentialsDto) {

		String username = credentialsDto.getUsername();

		String pw = credentialsDto.getPassword();

		List<User> allActiveUsers = userRepository.findAllByDeletedFalse();

		boolean usernameExists = false;

		for (User user : allActiveUsers) {

			// compare username from dto to every stored username

			if (username == user.getCredentials().getUsername()) {

				usernameExists = true;

				// then compare password attempt

				if (pw == user.getCredentials().getPassword()) {
					// password and username match then
					return user;
				}
			}
		}

		if (usernameExists) {
//			found username but passwords don't match
			throw new NotFoundException("Password does not match this username");

		} else {
//				username not found
			throw new NotFoundException("Username does not match any account");

		}

	}

	private Tweet validateTweet(Long tweetId) {
		if (tweetId == null) {
			throw new BadRequestException("Please enter a tweet id");
		}

		if (!tweetRepository.existsById(tweetId)) {
			throw new NotFoundException("Please enter a different tweet id");
		}

		Tweet tweet = tweetRepository.getById(tweetId);
		if (!tweet.isDeleted()) {
			throw new NotFoundException("This tweet is deleted. Please enter a different tweet id");
		}

		return tweet;

	}

	@Override
	public List<TweetResponseDto> getAllTweets() {
		return tweetMapper.entitiesToDtos(tweetRepository.findAllByDeletedFalse());
	}

	@Override
	public void likeTweet(Long tweetId, CredentialsDto credentialsDto) {

		User validatedUser = validateUser(credentialsDto);

		Tweet validatedTweet = validateTweet(tweetId);

		List<Tweet> userLikes = validatedUser.getLikes();

		userLikes.add(validatedTweet);

		List<User> usersWhoLikeThisTweet = validatedTweet.getUserLikes();

		usersWhoLikeThisTweet.add(validatedUser);

//		I don't think these need to be saved because this endpoint doesn't return anyDto 

//		However userRepository.flush() doesn't take any arguments to specify what should be flushed

		userRepository.saveAndFlush(validatedUser);

		tweetRepository.saveAndFlush(validatedTweet);

	}

}
