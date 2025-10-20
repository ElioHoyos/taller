package com.example.demo.validate;

import com.example.demo.entity.enums.DocumentType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentValidator {

    public List<String> validate(DocumentType documentType, String documentNumber) {
        List<String> errors = new ArrayList<>();

        if (documentType == null) {
            errors.add("Tipo de documento inválido");
            return errors;
        }
        if (documentNumber == null || documentNumber.trim().isEmpty()) {
            errors.add("El documento no puede estar vacío");
            return errors;
        }

        String n = documentNumber.trim();

        // Solo dígitos
        if (!n.matches("\\d+")) {
            errors.add("El documento solo debe contener dígitos");
            return errors;
        }

        switch (documentType) {
            case DNI:
                if (!n.matches("\\d{8}")) {
                    errors.add("El DNI debe tener 8 digitos");
                }
                break;
            case RUC:
                if (!n.matches("\\d{11}")) {
                    errors.add("El documento RUC debe tener 11 digitos");
                }
                break;
            default:
                errors.add("Tipo de documento inválido");
        }
        return errors;
    }
}
