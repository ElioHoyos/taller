package com.example.demo.service.impl;

import com.example.demo.entity.Rol;
import com.example.demo.repository.RolRepository;
import com.example.demo.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository repo;

    @Override
    public List<Rol> list() { return repo.findAll(); }

    @Override
    public Rol createOrUpdate(Rol r) {
        if (r.getId() == null) {
            if (repo.existsByName(r.getName())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rol duplicado");
            r.setDateCreated(LocalDate.now());
        } else {
            if (repo.existsByNameAndIdNot(r.getName(), r.getId())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rol duplicado");
        }
        r.setDateModified(LocalDate.now());
        return repo.save(r);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no existe");
        repo.deleteById(id);
    }
}
