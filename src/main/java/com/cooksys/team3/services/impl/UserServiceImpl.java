package com.cooksys.team3.services.impl;

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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.cooksys.team3.services.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
private final UserMapper userMapper;
private final UserRepository userRepository;
private final TweetMapper tweetMapper;
private final TweetRepository tweetRepository;


private void validateUser(UserRequestDto userRequestDto) {
    if(userRequestDto.getCredentialsDto() == null){
        throw new BadRequestException("Please enter username and password");
    }
    if(userRequestDto.getCredentialsDto().getUsername() == null){
        throw new BadRequestException("Please enter a username");
    }
    if (userRequestDto.getCredentialsDto().getPassword() == null){
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

    if(optional.isEmpty()){
        throw  new NotFoundException("Specified user could not be found");
    }
    List<Tweet> tweets = tweetRepository.findByAuthor(optional.get());
    return tweetMapper.entitiesToDtos(tweets);
    }

    @Override
    public List<UserResponseDto> getFollowers(String username) {
        return null;
    }
}
