package com.cooksys.team3.controllers;

import java.util.List;

import com.cooksys.team3.dtos.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.team3.services.TweetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

	private final TweetService tweetService;
	
	@GetMapping
	public List<TweetResponseDto> getAllTweets() {
		return tweetService.getAllTweets();
	}
	
	@GetMapping("/{id}")
	public TweetResponseDto getTweetById(@PathVariable Long id) {
		return tweetService.getTweetById(id);
	}
	
	@GetMapping("/{id}/likes")
	public List<UserResponseDto> getUsersWhoLikedTweet(@PathVariable Long id) {
		return tweetService.getUsersWhoLikedTweet(id);
	}
	
	@GetMapping("/{id}/replies")
	public List<TweetResponseDto> getReplies (@PathVariable Long id){
		return tweetService.getReplies(id);
	}
	
	@GetMapping("/{id}/reposts")
	public List<TweetResponseDto> getReposts(@PathVariable Long id) {
		return tweetService.getReposts(id);
	}
	
	@GetMapping("/{id}/context")
	public ContextDto getContextOfTweet(@PathVariable Long id) {
		return tweetService.getContextOfTweet(id);
	}
	
	@GetMapping("{id}/mentions")
	public List <UserResponseDto> getMentions (@PathVariable Long id){
		return tweetService.getMentions(id);
	}

	@PostMapping("/{id}/like")
	public void likeTweet(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
		tweetService.likeTweet(id, 	userRequestDto);
	}
	
	@PostMapping("/{id}/repost")
	public TweetResponseDto repostTweet(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
		return tweetService.repostTweet(id, userRequestDto);
	}

	@PostMapping("/{id}/reply")

	public TweetResponseDto replyTweet(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto) {
		return null;
	}

}
