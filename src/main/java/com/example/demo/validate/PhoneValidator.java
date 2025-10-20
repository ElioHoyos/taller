package com.example.demo.validate;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PhoneValidator {
    public List<String> validate(String cellphone) {
        List<String> errors = new ArrayList<>();
        if (cellphone == null || cellphone.isBlank()) return errors; // opcional
        if (!cellphone.trim().matches("\\d{9}")) {
            errors.add("Tu número de celular debe tener 9 digitos");
        }
        return errors;
    }
}
