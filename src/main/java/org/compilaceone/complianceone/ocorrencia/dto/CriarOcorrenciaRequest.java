package org.compilaceone.complianceone.ocorrencia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.compilaceone.complianceone.ocorrencia.domain.enums.TipoOcorrencia;

import java.time.LocalDateTime;

public record CriarOcorrenciaRequest(

        @NotNull(message = "O tipo da ocorrência é obrigatório")
        TipoOcorrencia tipo,

        @NotBlank(message = "O relato é obrigatório")
        @Size(min = 10, max = 5000,
                message = "O relato deve ter entre 10 e 5000 caracteres")
        String relato,

        @NotNull(message = "O campo anônima é obrigatório")
        Boolean anonima,

        @Size(max = 255,
                message = "O setor relacionado deve ter no máximo 255 caracteres")
        String setorRelacionado,

        LocalDateTime  dataOcorrencia

) {
}