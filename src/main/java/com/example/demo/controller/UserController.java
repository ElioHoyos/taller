package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.request.UserRequestDto;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/listUser")
    public List<UserDto> getAllUser(){
        return userService.getUsers();
    }

    @GetMapping("/{Id}")
    public Optional<User> getById(@PathVariable("Id") Long Id){
        return userService.getUser(Id);
    }

    @PostMapping
    public void saveUserToRol(@RequestBody UserRequestDto userRequestDto){
        userService.saveUserToRol(userRequestDto);
    }

}
