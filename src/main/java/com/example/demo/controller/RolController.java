package com.example.demo.controller;

import com.example.demo.dto.RolDto;
import com.example.demo.dto.request.RolRequestDto;
import com.example.demo.entity.Rol;
import com.example.demo.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/rol")
public class RolController {
    @Autowired
    private RolService rolService;

    @GetMapping("/listRol")
    public List<RolDto> getAllByArticle(){
        return rolService.getRoles();
    }

    @GetMapping("/{Id}")
    public Optional<Rol> getById(@PathVariable("Id") Long Id){
        return rolService.getRol(Id);
    }

    @PostMapping
    public void saveRol(@RequestBody RolRequestDto rolRequestDto){
        rolService.saveRol(rolRequestDto);
    }

}
