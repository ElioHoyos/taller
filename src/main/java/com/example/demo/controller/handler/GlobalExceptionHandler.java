package com.example.demo.controller.handler;

import com.example.demo.exception.CategoryNameEmptyException;
import com.example.demo.exception.ExternalLookupException;
import com.example.demo.exception.ValidationException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(ex.getErrors(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    if (error instanceof FieldError) {
                        FieldError fieldError = (FieldError) error;
                        return getCustomErrorMessage(fieldError.getField(), error.getDefaultMessage());
                    }
                    return error.getDefaultMessage();
                })
                .collect(Collectors.joining("; "));

        return new ResponseEntity<>(new ErrorResponse(errorMessage), HttpStatus.BAD_REQUEST);
    }


    private String getCustomErrorMessage(String fieldName, String defaultMessage) {
        switch (fieldName) {
            case "amount":
                return "Cantidad: " + formatAmountMessage(defaultMessage);
            case "sale_price":
                return "Precio de venta: " + formatPriceMessage(defaultMessage);
            case "purchase_price":
                return "Precio de compra: " + formatPriceMessage(defaultMessage);
            default:
                return defaultMessage;
        }
    }

    // Mensajes detallados para campos numéricos
    private String formatAmountMessage(String message) {
        return "Debe ser un número entero positivo (ej: 10). " +
                "No se aceptan negativos, letras ni caracteres especiales. " +
                message;
    }

    private String formatPriceMessage(String message) {
        return "Debe ser un número mayor que cero con hasta 2 decimales (ej: 15.99). " +
                "No se aceptan negativos, letras ni caracteres especiales. " +
                message;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CategoryNameEmptyException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNameEmptyException(CategoryNameEmptyException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("Adventencia", ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(CategoryNameEmptyException.CategoryNameDuplicateException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNameDuplicateException(CategoryNameEmptyException.CategoryNameDuplicateException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("Adventencia", ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(
            org.springframework.dao.DataIntegrityViolationException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("Adventencia", "El nombre de la categoría ya existe");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //Validación Customer
    @ExceptionHandler(com.example.demo.exception.DocumentValidationException.class)
    public ResponseEntity<Map<String, Object>> handleDocumentValidation(
            com.example.demo.exception.DocumentValidationException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("Advertencia", ex.getErrorMessages());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    //api reniec
    @ExceptionHandler(ExternalLookupException.class)
    public ResponseEntity<Map<String, Object>> handleExternalLookup(ExternalLookupException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("Advertencia", ex.getMessage());
        return ResponseEntity.status(ex.getStatus() >= 400 ? ex.getStatus() : 400).body(body);
    }

}