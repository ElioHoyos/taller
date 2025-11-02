package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.request.UserRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> getUsers();
    Optional<UserDto> getUser(Long id);
    UserDto create(UserRequestDto r);
    UserDto update(Long id, UserRequestDto r);
    void delete(Long id);
    UserDto toggleState(Long id);

    // imagen y password
    UserDto uploadImage(Long id, MultipartFile file);
    void changePassword(Long id, String newPassword);

    // roles
    UserDto setRoles(Long idUser, List<Long> roleIds);
    UserDto addRole(Long idUser, Long idRol);
    UserDto removeRole(Long idUser, Long idRol);
}
