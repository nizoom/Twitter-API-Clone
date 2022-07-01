package com.cooksys.team3.controllers;

import com.cooksys.team3.dtos.HashtagDto;
import com.cooksys.team3.dtos.TweetResponseDto;
import com.cooksys.team3.entities.Tweet;
import com.cooksys.team3.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")



public class HashtagController {

	private final HashtagService hashtagService;
	
	@GetMapping
	
	public List<HashtagDto> getTags() {
		return hashtagService.getTags();
	}

	@GetMapping ("/{label}")
	public List<TweetResponseDto> taggedTweets(String label){
		return hashtagService.taggedTweets(label);
	}


	}







