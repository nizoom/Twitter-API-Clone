package com.cooksys.team3.mappers;

import com.cooksys.team3.dtos.TweetRequestDto;
import com.cooksys.team3.dtos.TweetResponseDto;
import com.cooksys.team3.entities.Tweet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface TweetMapper {

    TweetResponseDto entityToDto(Tweet entity);
    List<TweetResponseDto> entitiesToDtos(List<Tweet> entities);

    Tweet requestEntity(TweetRequestDto tweetRequestDto);

}
