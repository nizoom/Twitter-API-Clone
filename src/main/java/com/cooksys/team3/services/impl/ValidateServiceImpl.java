package com.cooksys.team3.services.impl;

import org.springframework.stereotype.Service;

import com.cooksys.team3.repositories.HashtagRepository;
import com.cooksys.team3.services.ValidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {
	
	private final HashtagRepository hashtagRepository;
	
	
	@Override
	public boolean hashtagExists(String label) {
		return hashtagRepository.existsByLabel(label);
	}
}
