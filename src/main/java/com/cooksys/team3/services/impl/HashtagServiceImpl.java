package com.cooksys.team3.services.impl;

import com.cooksys.team3.dtos.HashtagDto;
import com.cooksys.team3.dtos.TweetResponseDto;
import com.cooksys.team3.entities.Hashtag;
import com.cooksys.team3.entities.Tweet;
import com.cooksys.team3.exceptions.NotFoundException;
import com.cooksys.team3.mappers.HashtagMapper;
import com.cooksys.team3.mappers.TweetMapper;
import com.cooksys.team3.repositories.HashtagRepository;
import com.cooksys.team3.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

	private final HashtagRepository hashtagRepository;
	private final HashtagMapper hashtagMapper;
	
	private final TweetMapper tweetMapper;

	@Override
	public List<HashtagDto> getTags() {
		return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
	}

	@Override
	public List<TweetResponseDto> taggedTweets(String label) {

		List<Hashtag> checkExist = hashtagRepository.findAllByLabel(label);
		if(checkExist.isEmpty()){
			throw new NotFoundException("This hashtag does not exist");
		}

		List<Tweet> allTagged = hashtagRepository.getHashtagTweets(label);
		List<Tweet> taggedNotDeleted = new ArrayList<>();
		for (Tweet tweet : allTagged) {
			if (!tweet.isDeleted()) {
				taggedNotDeleted.add(tweet);
			}
		}
		taggedNotDeleted.sort(Comparator.comparing(Tweet::getPosted).reversed());

		return tweetMapper.entitiesToDtos(taggedNotDeleted);
	}

}
