package org.compilaceone.complianceone.ocorrencia.dto;

import org.compilaceone.complianceone.ocorrencia.domain.enums.StatusOcorrencia;
import java.time.LocalDateTime;
import java.util.UUID;

public record HistoricoResponse(
        UUID id,
        LocalDateTime dataEvento,
        String tipo,
        String usuario,
        String emailUsuario,
        String ip,
        String origem,
        String descricao,
        StatusOcorrencia statusAnterior,
        StatusOcorrencia statusNovo
) {}
