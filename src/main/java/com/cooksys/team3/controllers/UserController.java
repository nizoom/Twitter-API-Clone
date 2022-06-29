package com.cooksys.team3.controllers;

import com.cooksys.team3.dtos.TweetResponseDto;
import com.cooksys.team3.dtos.UserRequestDto;
import com.cooksys.team3.dtos.UserResponseDto;
import com.cooksys.team3.services.UserService;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	private final UserService userService;

	@GetMapping
	public List<UserResponseDto> getUsers() {
		return userService.getUsers();
	}

	@GetMapping("/@{username}/followers")

	public List<UserResponseDto> getFollowers(@PathVariable String username) {
		return userService.getFollowers(username);
	}

	@GetMapping("/@{username}/tweets")

	public List<TweetResponseDto> getTweets(@PathVariable String username) {
		return userService.getTweets(username);
	}

	@GetMapping("/@{username}/feed")

	public List<TweetResponseDto> getFeed(@PathVariable String username) {
		return userService.getFeed(username);
	}

	@PatchMapping("/@{username}")

	public UserResponseDto updateUsername(@PathVariable String username, @RequestBody UserRequestDto userRequestDto) {
		return userService.updateUsername(username, userRequestDto);
	}

	@GetMapping("/@{username}/mentions")
	
	public List<TweetResponseDto> getMentions(@PathVariable String username) {
		return userService.getMentions(username);
	}

}
