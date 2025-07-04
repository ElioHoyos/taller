package com.example.demo.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ValidationException extends RuntimeException {
    private final Map<String, String> errors;

    public ValidationException(Map<String, String> errors) {
        super("Errores de validación detectados");
        this.errors = errors;
    }
}