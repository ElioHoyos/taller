package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // Esto hará que se devuelva un error 400
public class RolNameException extends RuntimeException {
    public RolNameException(String message) {
        super(message);
    }
}
