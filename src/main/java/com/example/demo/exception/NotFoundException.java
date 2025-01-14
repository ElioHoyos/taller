package com.example.demo.exception;
import lombok.Getter;
import java.util.List;

@Getter
public class NotFoundException extends RuntimeException {
    private List<NotFoundException> messages;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(List<NotFoundException> messages) {
        this.messages = messages;
    }
}
