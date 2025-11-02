package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.request.UserRequestDto;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public List<UserDto> list() { return service.getUsers(); }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(@PathVariable Long id) {
        return service.getUser(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserRequestDto r) {
        UserDto created = service.create(r);
        return ResponseEntity.created(URI.create("/api/v1/user/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UserRequestDto r) {
        return service.update(id, r);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle-state")
    public UserDto toggle(@PathVariable Long id) { return service.toggleState(id); }

    @PostMapping(path = "/{id}/image", consumes = {"multipart/form-data"})
    public UserDto uploadImage(@PathVariable Long id, @RequestPart("file") MultipartFile file) {
        return service.uploadImage(id, file);
    }

    @PostMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestParam String password) {
        service.changePassword(id, password);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/roles")
    public UserDto setRoles(@PathVariable Long id, @RequestBody List<Long> roleIds) {
        return service.setRoles(id, roleIds);
    }

    // agregar un rol puntual
    @PostMapping("/{id}/roles/{roleId}")
    public UserDto addRole(@PathVariable Long id, @PathVariable Long roleId) {
        return service.addRole(id, roleId);
    }

    // quitar un rol puntual
    @DeleteMapping("/{id}/roles/{roleId}")
    public UserDto removeRole(@PathVariable Long id, @PathVariable Long roleId) {
        return service.removeRole(id, roleId);
    }

}
