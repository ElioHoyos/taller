package com.example.demo.service.external;

import com.example.demo.dto.external.DniLookupResponse;
import com.example.demo.dto.external.RucLookupResponse;
import com.example.demo.exception.ExternalLookupException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExternalIdLookupServiceImpl implements ExternalIdLookupService {

    private final RestTemplate rt;

    @Value("${external.id.base-url}")
    private String baseUrl;
    @Value("${external.id.token}")
    private String token;
    @Value("${external.id.referer:https://apis.net.pe/consulta-dni-api}")
    private String referer;

    /* ================= DNI ================= */
    @Override
    public DniLookupResponse lookupDni(String dni) {
        if (dni == null || !dni.matches("\\d{8}")) {
            throw new ExternalLookupException("El DNI debe tener 8 digitos", 400);
        }
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/v1/dni")
                .queryParam("numero", dni)
                .toUriString();

        HttpHeaders h = new HttpHeaders();
        h.setBearerAuth(token);
        h.set("Referer", referer);
        h.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> req = new HttpEntity<>(h);

        try {
            ResponseEntity<Map<String, Object>> rsp =
                    rt.exchange(url, HttpMethod.GET, req, new ParameterizedTypeReference<Map<String, Object>>() {});
            Map<String, Object> b = rsp.getBody();

            if (b == null || b.get("numeroDocumento") == null) {
                throw new ExternalLookupException("DNI no encontrado", 404);
            }

            return DniLookupResponse.builder()
                    .dni(             str(b.get("numeroDocumento")))
                    .nombres(         str(b.get("nombres")))
                    .apellidoPaterno( str(b.get("apellidoPaterno")))
                    .apellidoMaterno( str(b.get("apellidoMaterno")))
                    .build();

        } catch (HttpStatusCodeException e) {
            throw new ExternalLookupException("Proveedor: " + e.getStatusCode().value()
                    + " " + e.getResponseBodyAsString(), e.getStatusCode().value());
        } catch (Exception e) {
            throw new ExternalLookupException("Error consultando DNI: " + e.getMessage(), 502);
        }
    }

    /* ================= RUC ================= */
    @Override
    public RucLookupResponse lookupRuc(String ruc) {
        if (ruc == null || !ruc.matches("\\d{11}")) {
            throw new ExternalLookupException("El documento RUC debe tener 11 digitos", 400);
        }

        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/v1/ruc")
                .queryParam("numero", ruc)
                .toUriString();

        HttpHeaders h = new HttpHeaders();
        h.setBearerAuth(token);
        h.set("Referer", referer);
        h.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> req = new HttpEntity<>(h);

        try {
            ResponseEntity<Map<String, Object>> rsp =
                    rt.exchange(url, HttpMethod.GET, req, new ParameterizedTypeReference<Map<String, Object>>() {});
            Map<String, Object> b = rsp.getBody();

            if (b == null || b.get("numeroDocumento") == null) {
                throw new ExternalLookupException("RUC no encontrado", 404);
            }

            return RucLookupResponse.builder()
                    .ruc(             str(b.get("numeroDocumento")))
                    .razonSocial(     str(b.get("razonSocial")))
                    .nombreComercial( str(b.get("nombreComercial")))
                    .estado(          str(b.get("estado")))
                    .condicion(       str(b.get("condicion")))
                    .direccion(       str(b.get("direccion")))
                    .departamento(    str(b.get("departamento")))
                    .provincia(       str(b.get("provincia")))
                    .distrito(        str(b.get("distrito")))
                    .build();

        } catch (HttpStatusCodeException e) {
            throw new ExternalLookupException("Proveedor: " + e.getStatusCode().value()
                    + " " + e.getResponseBodyAsString(), e.getStatusCode().value());
        } catch (Exception e) {
            throw new ExternalLookupException("Error consultando RUC: " + e.getMessage(), 502);
        }
    }

    /* ===== helpers ===== */
    private static String str(Object v) {
        return v == null ? "" : v.toString();
    }
}
