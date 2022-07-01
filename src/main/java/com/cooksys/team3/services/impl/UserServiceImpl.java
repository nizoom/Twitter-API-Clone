package com.cooksys.team3.services.impl;

import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.cooksys.team3.services.ValidateService;
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

	private final ValidateService validateService;

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

		Optional<User> userUpdate = userRepository.findByDeletedFalseAndCredentialsUsernameAndCredentialsPassword(username, userRequestDto.getCredentialsDto().getPassword());

		if(userUpdate.isEmpty()){
			throw new NotFoundException("Matching user could not be found");
		}
		if(userUpdate.get().getProfile().getEmail() == null) {
			throw new BadRequestException("you must input an email address");
		}
		if(username != userUpdate.get().getCredentials().getUsername()) {
			throw new BadRequestException("Provided username does not match Credentials");
		}
		User user = userUpdate.get();

		user.getProfile().setFirstName(userRequestDto.getProfileDto().getFirstName());
		user.getProfile().setLastName(userRequestDto.getProfileDto().getLastName());
		user.getProfile().setEmail(userRequestDto.getProfileDto().getEmail());
		user.getProfile().setPhone(userRequestDto.getProfileDto().getPhone());

		return userMapper.entityToDto(userRepository.saveAndFlush(user));






	}

	@Override
	public List<TweetResponseDto> getFeed(String username) {
		Optional<User> optional = userRepository.findByDeletedFalseAndCredentialsUsername(username);
		if (optional.isEmpty()) {
			throw new NotFoundException("Specified user could not be found");
		}

		//retrieves list of all tweets by user
		List<Tweet> tweets = optional.get().getTweets();

		//new list to contain non deleted tweets by user
		List<Tweet> nonDeletedTweets = new ArrayList<>();

		//adds all non deleted tweets to nonDeletedTweets
		for(Tweet allTweets: tweets) {
			if (!allTweets.isDeleted()) {
				nonDeletedTweets.add(allTweets);
			}
		}
		//retrieves all following
		List<User> following = optional.get().getFollowing();
		//sorts through following to find non deleted following and adds to list
		List<User> nonDeletedFollowing = new ArrayList<>();
		for(User follower: following) {
			if(!follower.isDeleted()){
				nonDeletedFollowing.add(follower);
			}
		}
		//adds tweets from everyone user is following to a list
		List<Tweet> followingTweets = new ArrayList<>();

		for(User user: nonDeletedFollowing){

			followingTweets.addAll(user.getTweets());

		}
		//sorts through all tweets from following and adds all non-deleted tweets to new list
		List<Tweet> undDeletedFollowingTweets = new ArrayList<>();

		for(Tweet tweet: followingTweets){
			if(!tweet.isDeleted()) {
				undDeletedFollowingTweets.add(tweet);
			}
		}
		List<Tweet> feed = undDeletedFollowingTweets;
		feed.addAll(nonDeletedTweets);

		feed.sort(Comparator.comparing(Tweet::getPosted).reversed());

		return tweetMapper.entitiesToDtos(feed);



	}

	@Override
	public List<TweetResponseDto> getTweets(String username) {
		Optional<User> optional = userRepository.findByDeletedFalseAndCredentialsUsername(username);

		if (optional.isEmpty()) {
			throw new NotFoundException("Specified user could not be found");
		}
		List<Tweet> tweets = optional.get().getTweets();
		List<Tweet> nonDeletedTweets = new ArrayList<>();
		for(Tweet tweet: tweets){
			if(!tweet.isDeleted()){
				nonDeletedTweets.add(tweet);
			}
		}
		nonDeletedTweets.sort(Comparator.comparing(Tweet::getPosted).reversed());
		return tweetMapper.entitiesToDtos(nonDeletedTweets);
	}

	@Override
	public List<UserResponseDto> getFollowers(String username) {
		Optional<User> optional = userRepository.findByDeletedFalseAndCredentialsUsername(username);

		if (optional.isEmpty()) {
			throw new NotFoundException("Specified user could not be found");
		}
		List<User> followers = optional.get().getFollowers();
		List<User> nonDeletedFollowers = new ArrayList<>();
			for(User follower: followers) {
				if(!follower.isDeleted()){
					nonDeletedFollowers.add(follower);
				}
			}
			return userMapper.entityToDto(nonDeletedFollowers);

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

	@Override
	public UserResponseDto getUser(String username) {
		Optional<User> requestedUser = userRepository.findByDeletedFalseAndCredentialsUsername(username);
		
		if(requestedUser.isEmpty()) {
			throw new NotFoundException("Specified user could not be found");
		}
		return userMapper.entityToDto(requestedUser.get());
	}

	@Override
	public void unfollowUser(String username, UserRequestDto userRequestDto) {
		validateUser(userRequestDto);
		Optional<User> pathUser = userRepository.findByDeletedFalseAndCredentialsUsername(username);
		Optional<User> credentialsUser = userRepository.findByDeletedFalseAndCredentialsUsername(userRequestDto.getCredentialsDto().getUsername());
		if(pathUser.isEmpty() || credentialsUser.isEmpty()) {
			throw new NotFoundException("Specified user could not be found");
		}
		User userWhoIsFollowing = credentialsUser.get();
		User userToBeUnfollowed = pathUser.get();
		
		List<User> followedUsers = userWhoIsFollowing.getFollowing();
		List<User> followers = userToBeUnfollowed.getFollowers();
		if(followedUsers.contains(userToBeUnfollowed)) {
			followedUsers.remove(userToBeUnfollowed);
			followers.remove(userWhoIsFollowing);
		}
		else {
			throw new NotFoundException("No following relationship exists between users");
		}
		userToBeUnfollowed.setFollowers(followers);
		userWhoIsFollowing.setFollowing(followedUsers);
		userRepository.saveAndFlush(userWhoIsFollowing);
		userRepository.saveAndFlush(userToBeUnfollowed);
	}
	

	@Override
	public UserResponseDto deleteUser(String username, UserRequestDto userRequestDto) {
	
		validateUser(userRequestDto);
		
		if(!userRepository.existsByCredentialsUsername(username)) {
			throw new NotFoundException("Specified user could not be found");
		}
		
		Optional<User> userToBeDeleted = userRepository.findByDeletedFalseAndCredentialsUsername(username);
		
//		change all tweets to be deleted as well 
		
		List <Tweet> tweetsToBeDeleted = userToBeDeleted.get().getTweets();
		for(Tweet tweet : tweetsToBeDeleted) {
			tweet.setDeleted(true);
		}
		
		tweetRepository.saveAllAndFlush(tweetsToBeDeleted);
		
		userToBeDeleted.get().setDeleted(true);
		User userToBeDeletedWithId = userRepository.saveAndFlush(userToBeDeleted.get());
		
		return userMapper.entityToDto(userToBeDeletedWithId);
		

	}

	@Override
	public void followUser(String username, UserRequestDto userRequestDto) {
		validateUser(userRequestDto);
		Optional<User> pathUser = userRepository.findByDeletedFalseAndCredentialsUsername(username);
		Optional<User> credentialsUser = userRepository.findByDeletedFalseAndCredentialsUsername(userRequestDto.getCredentialsDto().getUsername());
		if(pathUser.isEmpty() || credentialsUser.isEmpty()) {
			throw new NotFoundException("Specified user could not be found");
		}
		User userWhoWantsToFollow = credentialsUser.get();
		User userToBeFollowed = pathUser.get();
		
		List<User> followedUsers = userWhoWantsToFollow.getFollowing();
		List<User> followers = userToBeFollowed.getFollowers();
		
		if(followedUsers.contains(userToBeFollowed)) {
			throw new BadRequestException("That user is already followed.");
		}
		else {
			followedUsers.add(userToBeFollowed);
			followers.add(userWhoWantsToFollow);
		}
		userToBeFollowed.setFollowers(followers);
		userWhoWantsToFollow.setFollowing(followedUsers);
		userRepository.saveAndFlush(userWhoWantsToFollow);
		userRepository.saveAndFlush(userToBeFollowed);
		
	}

	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		//ensure required profile and credentials fields are entered 
		validateUser(userRequestDto);
		if(userRequestDto.getProfileDto().getEmail().isEmpty()) {
			throw new BadRequestException("Please enter an email address");
		}
		String pw = userRequestDto.getCredentialsDto().getPassword();
		String username = userRequestDto.getCredentialsDto().getUsername();
		
		Optional <User> existingUser = userRepository.findByCredentialsUsername(username);
		
		if(existingUser == null) {
			
		    long now = System.currentTimeMillis();
	        Timestamp sqlTimestamp = new Timestamp(now);
	
			User newUser = userMapper.dtoToEntity(userRequestDto);
			newUser.setDeleted(false);
			newUser.setJoined(sqlTimestamp);
			
			
			User userToReturn = userRepository.saveAndFlush(newUser);
			return userMapper.entityToDto(userToReturn);
			
			
		} else if(existingUser.get().getCredentials().getPassword() == pw && existingUser.get().isDeleted()) {
//				change to deleted is false 
			existingUser.get().setDeleted(false);

			List<Tweet> deletedTweets = existingUser.get().getTweets();
			
			for(Tweet tweet : deletedTweets) {
				tweet.setDeleted(false);
			}
			
			User existingUserToReturn = userRepository.saveAndFlush(existingUser.get());
			tweetRepository.saveAll(deletedTweets);
			
			return userMapper.entityToDto(existingUserToReturn);
			
		} else {
			throw new BadRequestException("Username not available. Please try a different username");
		}
					
	}
}
