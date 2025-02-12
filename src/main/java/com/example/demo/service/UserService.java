package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.request.UserRequestDto;
import com.example.demo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> getUsers();
    Optional<User> getUser(Long id);
    void saveUserToRol(UserRequestDto userRequestDto);
}
