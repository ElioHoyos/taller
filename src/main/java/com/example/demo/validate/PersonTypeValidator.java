package com.example.demo.validate;

import com.example.demo.dto.request.CustomerRequestDto;
import com.example.demo.entity.enums.PersonType;

import java.util.ArrayList;
import java.util.List;

public class PersonTypeValidator {
    public List<String> validate(CustomerRequestDto requestDao) {
        List<String> errorMessages = new ArrayList<>();

        // Validar el tipo de persona
        if (requestDao.getType_person() == null ||
                (!requestDao.getType_person().equals(PersonType.Cliente) &&
                        !requestDao.getType_person().equals(PersonType.Proveedor))) {
            errorMessages.add("Advertencia: \"No tenemos registrado este dato\"");
        }

        return errorMessages;
    }
}
