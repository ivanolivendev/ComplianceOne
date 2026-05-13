package org.compilaceone.complianceone.ocorrencia.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.compilaceone.complianceone.ocorrencia.domain.enums.StatusOcorrencia;
import org.compilaceone.complianceone.ocorrencia.dto.CriarOcorrenciaRequest;
import org.compilaceone.complianceone.ocorrencia.dto.OcorrenciaResponse;
import org.compilaceone.complianceone.ocorrencia.service.OcorrenciaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ocorrencias")
@RequiredArgsConstructor
public class OcorrenciaController {

    private final OcorrenciaService service;

    @PostMapping
    public ResponseEntity<OcorrenciaResponse> criar(@Valid @RequestBody CriarOcorrenciaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @GetMapping
    public ResponseEntity<Page<OcorrenciaResponse>> listarTodas(
            @PageableDefault(size = 10, sort = "dataCriacao") Pageable pageable
    ) {
        return ResponseEntity.ok(service.listarTodas(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OcorrenciaResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/protocolo/{protocolo}")
    public ResponseEntity<OcorrenciaResponse> buscarPorProtocolo(@PathVariable String protocolo) {
        return ResponseEntity.ok(service.buscarPorProtocolo(protocolo));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OcorrenciaResponse> atualizarStatus(
            @PathVariable UUID id,
            @RequestParam StatusOcorrencia status,
            @RequestParam(required = false) String observacao
    ) {
        return ResponseEntity.ok(service.atualizarStatus(id, status, observacao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}