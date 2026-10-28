package org.compilaceone.complianceone.ocorrencia.controller;

import lombok.RequiredArgsConstructor;
import org.compilaceone.complianceone.ocorrencia.service.AnexoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController @RequiredArgsConstructor
@RequestMapping("/api/v1/anexos")
public class AnexoDownloadController {
    private final AnexoService service;
    @GetMapping("/{anexoId}/conteudo")
    public ResponseEntity<byte[]> download(@PathVariable UUID anexoId) { return service.download(anexoId); }
}
