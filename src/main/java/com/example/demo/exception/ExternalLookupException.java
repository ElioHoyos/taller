package com.example.demo.exception;

import lombok.Getter;

@Getter
public class ExternalLookupException extends RuntimeException {
    private final int status;
    public ExternalLookupException(String message, int status) {
        super(message);
        this.status = status;
    }
}