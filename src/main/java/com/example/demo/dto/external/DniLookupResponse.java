package com.example.demo.dto.external;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DniLookupResponse {
    private String dni;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    // agrega campos si tu proveedor los ofrece (verificación, etc.)
}