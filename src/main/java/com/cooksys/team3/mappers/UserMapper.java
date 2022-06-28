package com.cooksys.team3.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.team3.dtos.UserResponseDto;
import com.cooksys.team3.entities.User;

@Mapper(componentModel = "spring")

public interface UserMapper {
	@Mapping(target="username", source="credentials.username")
	UserResponseDto entityToDto(User entity);
}
