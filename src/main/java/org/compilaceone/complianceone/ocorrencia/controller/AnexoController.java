package org.compilaceone.complianceone.ocorrencia.controller;

import lombok.RequiredArgsConstructor;
import org.compilaceone.complianceone.ocorrencia.dto.AnexoResponse;
import org.compilaceone.complianceone.ocorrencia.service.AnexoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
import java.io.IOException;

@RestController @RequiredArgsConstructor
@RequestMapping("/api/v1/ocorrencias/{ocorrenciaId}/anexos")
public class AnexoController {
    private final AnexoService service;
    @GetMapping public List<AnexoResponse> listar(@PathVariable UUID ocorrenciaId) { return service.listar(ocorrenciaId); }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AnexoResponse> upload(@PathVariable UUID ocorrenciaId, @RequestPart("file") MultipartFile file) throws IOException { return ResponseEntity.status(HttpStatus.CREATED).body(service.upload(ocorrenciaId, file)); }

}
