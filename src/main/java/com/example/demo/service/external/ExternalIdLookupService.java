package com.example.demo.service.external;

import com.example.demo.dto.external.DniLookupResponse;
import com.example.demo.dto.external.RucLookupResponse;

public interface ExternalIdLookupService {
    DniLookupResponse lookupDni(String dni);
    RucLookupResponse lookupRuc(String ruc);
}