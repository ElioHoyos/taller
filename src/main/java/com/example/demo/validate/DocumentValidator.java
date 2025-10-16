package com.example.demo.validate;

import com.example.demo.entity.enums.DocumentType;

import java.util.ArrayList;
import java.util.List;

public class DocumentValidator {

    public List<String> validate(DocumentType documentType, String documentNumber) {
        List<String> errors = new ArrayList<>();

        // null-safety básico
        if (documentType == null) {
            errors.add("Tipo de documento inválido");
            return errors;
        }
        if (documentNumber == null) {
            errors.add("El documento no puede estar vacío");
            return errors;
        }

        // normalizamos
        String number = documentNumber.trim();

        switch (documentType) {
            case DNI:
                // Requisito del usuario: devolver exactamente este texto cuando no tenga 8
                if (number.length() != 8) {
                    errors.add("El DNI debe tener 8 digitos");
                }
                break;
            case RUC:
                // Requisito: 11 dígitos exactos
                if (number.length() != 11) {
                    errors.add("El RUC debe tener 11 digitos");
                }
                break;
            default:
                errors.add("Tipo de documento inválido");
        }

        return errors;
    }
}
