package com.example.demo.service;

import com.example.demo.entity.Rol;

import java.util.List;

public interface RolService {
    List<Rol> list();
    Rol createOrUpdate(Rol r);
    void delete(Long id);
}
