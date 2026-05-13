package org.compilaceone.complianceone.ocorrencia.service;

import lombok.RequiredArgsConstructor;
import org.compilaceone.complianceone.ocorrencia.domain.entity.Ocorrencia;
import org.compilaceone.complianceone.ocorrencia.domain.enums.StatusOcorrencia;
import org.compilaceone.complianceone.ocorrencia.dto.CriarOcorrenciaRequest;
import org.compilaceone.complianceone.ocorrencia.dto.OcorrenciaResponse;
import org.compilaceone.complianceone.ocorrencia.repository.OcorrenciaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OcorrenciaService {

    private final OcorrenciaRepository repository;

    @Transactional
    public OcorrenciaResponse criar(CriarOcorrenciaRequest request) {
        Ocorrencia ocorrencia = Ocorrencia.builder()
                .protocolo(gerarProtocolo())
                .tipo(request.tipo())
                .relato(request.relato())
                .anonima(request.anonima())
                .setorRelacionado(request.setorRelacionado())
                .dataOcorrencia(request.dataOcorrencia())
                .status(StatusOcorrencia.RECEBIDA)
                .build();

        repository.save(ocorrencia);
        return mapToResponse(ocorrencia);
    }

    public Page<OcorrenciaResponse> listarTodas(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::mapToResponse);
    }

    public OcorrenciaResponse buscarPorId(UUID id) {
        Ocorrencia ocorrencia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));
        return mapToResponse(ocorrencia);
    }

    public OcorrenciaResponse buscarPorProtocolo(String protocolo) {
        Ocorrencia ocorrencia = repository.findByProtocolo(protocolo)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada com protocolo: " + protocolo));
        return mapToResponse(ocorrencia);
    }

    @Transactional
    public OcorrenciaResponse atualizarStatus(UUID id, StatusOcorrencia status, String observacao) {
        Ocorrencia ocorrencia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));

        ocorrencia.setStatus(status);
        ocorrencia.setObservacao(observacao);

        repository.save(ocorrencia);
        return mapToResponse(ocorrencia);
    }

    @Transactional
    public void deletar(UUID id) {
        Ocorrencia ocorrencia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));

        ocorrencia.setAtivo(false);
        repository.save(ocorrencia);
    }

    private OcorrenciaResponse mapToResponse(Ocorrencia ocorrencia) {
        return new OcorrenciaResponse(
                ocorrencia.getId(),
                ocorrencia.getProtocolo(),
                ocorrencia.getTipo(),
                ocorrencia.getRelato(),
                ocorrencia.getSetorRelacionado(),
                ocorrencia.getStatus(),
                ocorrencia.getObservacao(),
                ocorrencia.getAnonima(),
                ocorrencia.getDataCriacao()
        );
    }

    private String gerarProtocolo() {
        return "NR1-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}