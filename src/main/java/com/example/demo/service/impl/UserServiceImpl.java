package com.example.demo.service.impl;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.request.UserRequestDto;
import com.example.demo.entity.Rol;
import com.example.demo.entity.User;
import com.example.demo.repository.RolRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<UserDto> getUsers() {
        return List.of();
    }

    @Override
    public Optional<User> getUser(Long id) {
        return Optional.empty();
    }

    @Override
    public void saveUserToRol(UserRequestDto userRequestDto) {
        // Validaciones
        if (userRequestDto.getRol_id() == null) {
            throw new IllegalArgumentException("El rol no puede ser nulo");
        }

        // Buscar el rol en la base de datos
        Rol rol = rolRepository.findById(userRequestDto.getRol_id())
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con ID: " + userRequestDto.getRol_id()));

        User user = User.builder()
                .rol(rol)
                .name(userRequestDto.getName())
                .document_type(userRequestDto.getDocument_type())
                .document_number(userRequestDto.getDocument_number())
                .address(userRequestDto.getAddress())
                .cellphone(userRequestDto.getCellphone())
                .email(userRequestDto.getEmail())
                .state(Boolean.TRUE)
                .date_modified(LocalDate.now())
                .date_created(LocalDate.now())
                .build();
        userRepository.save(user);
    }
}
