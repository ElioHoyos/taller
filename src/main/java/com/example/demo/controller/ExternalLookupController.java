package com.example.demo.controller;

import com.example.demo.dto.external.DniLookupResponse;
import com.example.demo.dto.external.RucLookupResponse;
import com.example.demo.service.external.ExternalIdLookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lookup")
@RequiredArgsConstructor
public class ExternalLookupController {

    private final ExternalIdLookupService service;

    @GetMapping("/dni/{dni}")
    public ResponseEntity<DniLookupResponse> dni(@PathVariable String dni) {
        return ResponseEntity.ok(service.lookupDni(dni));
    }

    @GetMapping("/ruc/{ruc}")
    public ResponseEntity<RucLookupResponse> ruc(@PathVariable String ruc) {
        return ResponseEntity.ok(service.lookupRuc(ruc));
    }
}