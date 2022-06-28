package com.cooksys.team3.services.impl;

import com.cooksys.team3.dtos.UserResponseDto;
import com.cooksys.team3.mappers.UserMapper;
import com.cooksys.team3.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.cooksys.team3.services.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
private final UserMapper userMapper;
private final UserRepository userRepository;
    @Override
    public List<UserResponseDto> getUsers() {
        return userMapper.entityToDto(userRepository.findAllByDeletedFalse());
    }
}
