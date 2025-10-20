package com.example.demo.dto.external;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RucLookupResponse {
    private String ruc;
    private String razonSocial;
    private String nombreComercial;
    private String estado;      // ACTIVO/BAJA
    private String condicion;   // HABIDO/NO HABIDO
    private String direccion;
    private String departamento;
    private String provincia;
    private String distrito;
}