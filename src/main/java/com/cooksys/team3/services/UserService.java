package com.cooksys.team3.services;

import com.cooksys.team3.dtos.TweetResponseDto;
import com.cooksys.team3.dtos.UserRequestDto;
import com.cooksys.team3.dtos.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getUsers();


    UserResponseDto updateUsername(String username, UserRequestDto userRequestDto);

    List<TweetResponseDto> getFeed(String username);

    List<TweetResponseDto> getTweets(String username);

    List<UserResponseDto> getFollowers(String username);
}
