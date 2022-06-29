package com.cooksys.team3.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.team3.dtos.HashtagDto;
import com.cooksys.team3.mappers.HashtagMapper;
import com.cooksys.team3.repositories.HashtagRepository;
import com.cooksys.team3.services.HashtagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
	
	private HashtagRepository hashtagRepository;
	private HashtagMapper hashtagMapper;
	
	@Override
	public List<HashtagDto> getTags() {
		return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
	}
}
