package org.compilaceone.complianceone.ocorrencia.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.compilaceone.complianceone.ocorrencia.dto.CriarOcorrenciaRequest;
import org.compilaceone.complianceone.ocorrencia.dto.OcorrenciaResponse;
import org.compilaceone.complianceone.ocorrencia.service.OcorrenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ocorrencias")
@RequiredArgsConstructor
public class OcorrenciaController {

    private final OcorrenciaService service;

    @PostMapping
    public ResponseEntity<OcorrenciaResponse> criar(
            @Valid @RequestBody CriarOcorrenciaRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.criar(request));
    }
}

