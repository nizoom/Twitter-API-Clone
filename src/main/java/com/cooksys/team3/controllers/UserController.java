package com.cooksys.team3.controllers;

import com.cooksys.team3.dtos.UserResponseDto;
import com.cooksys.team3.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
 private final UserService userService;


 @GetMapping

 public List<UserResponseDto> getUsers() {
  return userService.getUsers();
 }


}
