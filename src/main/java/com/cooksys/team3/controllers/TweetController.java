package com.cooksys.team3.controllers;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.team3.dtos.TweetResponseDto;
import com.cooksys.team3.dtos.UserRequestDto;
import com.cooksys.team3.services.TweetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweet")

public class TweetController {
	
//	POST tweets/{id}/like
	
	@PostMapping("id/{id}/like")
	
	public ResponseEntity <TweetResponseDto> likeTweet(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto){
		
		return TweetService.likeTweet(id, userRequestDto);
	}

}
