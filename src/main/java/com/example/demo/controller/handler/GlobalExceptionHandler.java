package com.example.demo.controller.handler;

import com.example.demo.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
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
//exception de segun el documento de identidad de una persona
    @ExceptionHandler(DocumentValidationException.class)
    public ResponseEntity<Object> handleDocumentValidationException(DocumentValidationException ex) {
        // Crear un mapa para la respuesta
        Map<String, List<String>> response = new HashMap<>();
        response.put("Advertencia", ex.getErrorMessages());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //identificar si el nombre del rol esta vacio
    @ExceptionHandler(RolNameException.class)
    public ResponseEntity<Map<String, String>> handleRolException(RolNameException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("Advertencia", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
