package com.example.demo.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class DocumentValidationException extends RuntimeException {
    private final List<String> errorMessages;

    public DocumentValidationException(List<String> errorMessages) {
        super("Errores de validación de documento");
        this.errorMessages = errorMessages;
    }

}
