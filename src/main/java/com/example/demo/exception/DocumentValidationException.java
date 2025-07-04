package com.example.demo.exception;

import java.util.List;

public class DocumentValidationException extends RuntimeException {
    private final List<String> errorMessages;

    public DocumentValidationException(List<String> errorMessages) {
        super("Errores en la validación del documento");
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}