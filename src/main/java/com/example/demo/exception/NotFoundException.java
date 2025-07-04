package com.example.demo.exception;
import lombok.Getter;
import java.util.List;

@Getter
public class NotFoundException extends RuntimeException {
    private final List<NotFoundException> messages;

    public NotFoundException(String message) {
        super(message);
        this.messages = null;
    }

    public NotFoundException(List<NotFoundException> messages) {
        super(messages.get(0).getMessage());
        this.messages = messages;
    }
}