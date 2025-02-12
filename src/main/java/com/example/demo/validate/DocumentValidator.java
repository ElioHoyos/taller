package com.example.demo.validate;

import com.example.demo.entity.enums.DocumentType;
import java.util.ArrayList;
import java.util.List;

public class DocumentValidator {
    public List<String> validate(DocumentType documentType, String documentNumber) {
        List<String> errorMessages = new ArrayList<>();

        // Validación del tipo de documento
        switch (documentType) {
            case DNI:
                if (String.valueOf(documentNumber).length() != 8) {
                    errorMessages.add("El DNI debe tener exactamente 8 dígitos.");
                }
                break;
            case RUC:
                if (String.valueOf(documentNumber).length() != 11) {
                    errorMessages.add("El RUC debe tener exactamente 11 dígitos.");
                }
                break;
            default:
                errorMessages.add("Tipo de documento no válido. Debe ser 'DNI' o 'RUC'.");
                break;
        }

        return errorMessages;
    }
}
