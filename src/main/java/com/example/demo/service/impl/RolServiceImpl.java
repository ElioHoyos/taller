package com.example.demo.service.impl;

import com.example.demo.dto.RolDto;
import com.example.demo.dto.request.RolRequestDto;
import com.example.demo.entity.Rol;
import com.example.demo.exception.RolNameException;
import com.example.demo.repository.RolRepository;
import com.example.demo.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<RolDto> getRoles() {
        return List.of();
    }

    @Override
    public Optional<Rol> getRol(Long id) {
        return Optional.empty();
    }

    @Override
    public void saveRol(RolRequestDto rolRequestDto) {
        // Validar que el nombre no esté vacío
        if (rolRequestDto.getName() == null || rolRequestDto.getName().trim().isEmpty()) {
            throw new RolNameException("El nombre del rol no debe estar vacío.");
        }

        // Guardar el rol si la validación pasa
        rolRepository.save(Rol.builder()
                .name(rolRequestDto.getName())
                .description(rolRequestDto.getDescription())
                .date_modified(LocalDate.now())
                .date_created(LocalDate.now())
                .build());
    }
}
