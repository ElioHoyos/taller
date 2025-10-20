package com.example.demo.validate;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class EmailValidator {
    private static final Pattern P =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public List<String> validate(String email) {
        List<String> errors = new ArrayList<>();
        if (email == null || email.isBlank()) return errors; // opcional
        if (!P.matcher(email.trim()).matches()) {
            errors.add("El correo electrónico no es válido");
        }
        return errors;
    }
}
