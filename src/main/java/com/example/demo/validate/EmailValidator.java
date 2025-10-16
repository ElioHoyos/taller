package com.example.demo.validate;

import java.util.ArrayList;
import java.util.List;

public class EmailValidator {

    public List<String> validate(String email) {
        List<String> errors = new ArrayList<>();

        // Mensaje pedido por el usuario (claro y único)
        final String invalidMsg = "El correo electrónico no es válido";

        if (email == null) {
            errors.add(invalidMsg);
            return errors;
        }

        String value = email.trim();
        // Regex simple y suficiente para la mayoría de casos
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if (!value.matches(emailRegex)) {
            errors.add(invalidMsg);
        }

        return errors;
    }
}
