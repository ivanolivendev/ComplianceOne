package org.compilaceone.complianceone.ocorrencia.dto;

import org.compilaceone.complianceone.ocorrencia.domain.enums.StatusOcorrencia;

import java.time.LocalDateTime;
import java.util.UUID;

public record OcorrenciaResponse(

        UUID id,

        String protocolo,

        StatusOcorrencia status,

        LocalDateTime dataCriacao

) {
}