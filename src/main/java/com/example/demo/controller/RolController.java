package com.example.demo.controller;

import com.example.demo.entity.Rol;
import com.example.demo.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rol")
@RequiredArgsConstructor
public class RolController {

    private final RolService service;

    @GetMapping
    public List<Rol> list() { return service.list(); }

    @PostMapping
    public ResponseEntity<Rol> create(@RequestBody Rol r) {
        Rol created = service.createOrUpdate(r);
        return ResponseEntity.created(URI.create("/api/v1/rol/"+created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public Rol update(@PathVariable Long id, @RequestBody Rol r) {
        r.setId(id);
        return service.createOrUpdate(r);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
