package com.example.demo.validate;

import java.util.ArrayList;
import java.util.List;

public class EmailValidator {

    public List<String> validate(String email) {
        List<String> errorMessages = new ArrayList<>();
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        if (email == null || !email.matches(emailRegex)) {
            errorMessages.add("El correo electrónico debe ser en el siguiente formato: ejemplo@gmail.com.");
        }

        return errorMessages;
    }
}
