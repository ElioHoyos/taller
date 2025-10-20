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

/**
 * Manejador global de excepciones para controladores REST.
 *
 * Buenas prácticas implementadas:
 * 1. Centralización del manejo de errores (@RestControllerAdvice)
 * 2. Respuestas estructuradas y consistentes
 * 3. Personalización de mensajes de error para diferentes escenarios
 * 4. Separación de preocupaciones por tipo de excepción
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de validación personalizadas.
     * Devuelve un mapa detallado de errores con estado HTTP 400.
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
        // Buenas prácticas:
        // - Respuesta estructurada con todos los errores
        // - Código HTTP específico (400 Bad Request)
        return new ResponseEntity<>(ex.getErrors(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja errores de validación de Spring (@Valid).
     * Consolida múltiples errores en un solo mensaje.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Buenas prácticas:
        // - Procesamiento de todos los errores en un solo mensaje
        // - Mensajes personalizados para campos específicos
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

    /**
     * Personaliza mensajes de error para campos específicos.
     * Mejora la claridad para el cliente.
     */
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

    /**
     * Maneja recursos no encontrados (404 Not Found).
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        // Buenas prácticas:
        // - Mensaje claro de recurso no encontrado
        // - Código HTTP adecuado (404)
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja argumentos ilegales (400 Bad Request).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        // Buenas prácticas:
        // - Respuesta consistente para errores de lógica
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * ExceptionGlobal Category
     */

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