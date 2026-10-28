package org.compilaceone.complianceone.ocorrencia.service;

import lombok.RequiredArgsConstructor;
import org.compilaceone.complianceone.ocorrencia.domain.entity.Anexo;
import org.compilaceone.complianceone.ocorrencia.domain.entity.Ocorrencia;
import org.compilaceone.complianceone.ocorrencia.dto.AnexoResponse;
import org.compilaceone.complianceone.ocorrencia.repository.AnexoRepository;
import org.compilaceone.complianceone.ocorrencia.repository.OcorrenciaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.*;

@Service @RequiredArgsConstructor
public class AnexoService {
    private final AnexoRepository anexoRepository;
    private final OcorrenciaRepository ocorrenciaRepository;
    @Value("${supabase.url:}") private String supabaseUrl;
    @Value("${supabase.service-key:}") private String serviceKey;
    @Value("${supabase.storage-bucket:complice}") private String bucket;

    public List<AnexoResponse> listar(UUID ocorrenciaId) {
        return anexoRepository.findByOcorrenciaIdOrderByDataUploadDesc(ocorrenciaId).stream().map(AnexoResponse::from).toList();
    }

    public AnexoResponse upload(UUID ocorrenciaId, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) throw new IllegalArgumentException("Arquivo vazio");
        if (file.getSize() > 10 * 1024 * 1024) throw new IllegalArgumentException("O arquivo excede 10 MB");
        String contentType = Optional.ofNullable(file.getContentType()).orElse("application/octet-stream");
        Set<String> allowed = Set.of("application/pdf", "image/jpeg", "image/png", "image/webp", "text/plain");
        if (!allowed.contains(contentType)) throw new IllegalArgumentException("Tipo de arquivo não permitido");
        if (supabaseUrl.isBlank() || serviceKey.isBlank()) throw new IllegalStateException("Storage do Supabase não configurado");
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(ocorrenciaId).orElseThrow(() -> new NoSuchElementException("Ocorrência não encontrada"));
        String safeName = Optional.ofNullable(file.getOriginalFilename()).orElse("arquivo").replaceAll("[^a-zA-Z0-9._-]", "_");
        String path = ocorrenciaId + "/" + UUID.randomUUID() + "-" + safeName;
        HttpHeaders headers = new HttpHeaders(); headers.setBearerAuth(serviceKey); headers.set("apikey", serviceKey); headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        RestClient.create().put().uri(supabaseUrl + "/storage/v1/object/" + bucket + "/" + path)
                .headers(h -> h.addAll(headers)).body(file.getResource()).retrieve().toBodilessEntity();
        Anexo anexo = Anexo.builder().ocorrencia(ocorrencia).nomeArquivo(safeName).nomeOriginal(file.getOriginalFilename()).tamanho(file.getSize()).tipoArquivo(contentType).hashArquivo(hash(file.getBytes())).storagePath(path).build();
        return AnexoResponse.from(anexoRepository.save(anexo));
    }

    public ResponseEntity<byte[]> download(UUID anexoId) {
        Anexo anexo = anexoRepository.findById(anexoId).orElseThrow(() -> new NoSuchElementException("Anexo não encontrado"));
        byte[] data = RestClient.create().get().uri(supabaseUrl + "/storage/v1/object/" + bucket + "/" + anexo.getStoragePath())
                .header("Authorization", "Bearer " + serviceKey).header("apikey", serviceKey)
                .retrieve().body(byte[].class);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(anexo.getTipoArquivo())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + anexo.getNomeOriginal().replace("\"", "") + "\"").body(data);
    }

    private String hash(byte[] data) {
        try { byte[] digest = MessageDigest.getInstance("SHA-256").digest(data); StringBuilder out = new StringBuilder(); for (byte b : digest) out.append(String.format("%02x", b)); return out.toString(); }
        catch (Exception e) { throw new IllegalStateException("Não foi possível calcular o hash", e); }
    }
}
