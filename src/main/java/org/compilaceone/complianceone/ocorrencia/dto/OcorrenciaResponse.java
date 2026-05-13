package org.compilaceone.complianceone.ocorrencia.dto;

import org.compilaceone.complianceone.ocorrencia.domain.enums.StatusOcorrencia;
import org.compilaceone.complianceone.ocorrencia.domain.enums.TipoOcorrencia;

import java.time.LocalDateTime;
import java.util.UUID;

public record OcorrenciaResponse(

        UUID id,

        String protocolo,

        TipoOcorrencia tipo,

        String relato,

        String setorRelacionado,

        StatusOcorrencia status,

        String observacao,

        LocalDateTime dataCriacao

) {
}