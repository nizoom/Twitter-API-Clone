package com.cooksys.team3.mappers;

import java.util.List;
import java.util.Optional;

import org.mapstruct.Mapper;

import com.cooksys.team3.dtos.TweetRequestDto;
import com.cooksys.team3.dtos.TweetResponseDto;
import com.cooksys.team3.entities.Tweet;

@Mapper(componentModel = "spring")

public interface TweetMapper {

	TweetResponseDto entityToDto(Optional<Tweet> entity);

	List<TweetResponseDto> entitiesToDtos(List<Tweet> entities);

	Tweet requestEntity(TweetRequestDto tweetRequestDto);

}
