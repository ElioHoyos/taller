package com.example.demo.controller.handler;

import com.example.demo.exception.CategoryNameEmptyException;
import com.example.demo.exception.ErrorResponse;
import com.example.demo.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNameEmptyException.class)
    public ResponseEntity<String> handleCategoryNameEmptyException(CategoryNameEmptyException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNameEmptyException.CategoryNameDuplicateException.class)
    public ResponseEntity<String> handleCategoryNameDuplicateException(CategoryNameEmptyException.CategoryNameDuplicateException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT); // 409 Conflict
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleArticleException(NotFoundException ex) {
        // Crea una instancia de ErrorResponse con el mensaje de la excepción
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        // Devuelve la respuesta con el objeto ErrorResponse y el estado HTTP 400 (BAD_REQUEST)
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
