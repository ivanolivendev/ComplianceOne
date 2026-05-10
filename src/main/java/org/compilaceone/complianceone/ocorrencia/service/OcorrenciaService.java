package org.compilaceone.complianceone.ocorrencia.service;

import lombok.RequiredArgsConstructor;
import org.compilaceone.complianceone.ocorrencia.domain.entity.Ocorrencia;
import org.compilaceone.complianceone.ocorrencia.domain.enums.StatusOcorrencia;
import org.compilaceone.complianceone.ocorrencia.dto.CriarOcorrenciaRequest;
import org.compilaceone.complianceone.ocorrencia.dto.OcorrenciaResponse;
import org.compilaceone.complianceone.ocorrencia.repository.OcorrenciaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OcorrenciaService {

    private final OcorrenciaRepository repository;

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

        return new OcorrenciaResponse(
                ocorrencia.getId(),
                ocorrencia.getProtocolo(),
                ocorrencia.getStatus(),
                ocorrencia.getDataCriacao()
        );
    }

    private String gerarProtocolo() {
        return "NR1-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();

    }
}
