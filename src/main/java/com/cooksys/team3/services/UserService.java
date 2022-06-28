package com.cooksys.team3.services;

import com.cooksys.team3.dtos.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getUsers();
}
