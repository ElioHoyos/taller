package com.example.demo.service;

import com.example.demo.dto.RolDto;
import com.example.demo.dto.request.RolRequestDto;
import com.example.demo.entity.Rol;

import java.util.List;
import java.util.Optional;

public interface RolService {
    List<RolDto> getRoles();
    Optional<Rol> getRol(Long id);
    void saveRol(RolRequestDto rolRequestDto);
}
