package org.compilaceone.complianceone.ocorrencia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.compilaceone.complianceone.ocorrencia.domain.enums.TipoOcorrencia;

import java.time.LocalDateTime;

public record CriarOcorrenciaRequest(@NotNull
                                     TipoOcorrencia tipo,

                                     @NotBlank
                                     String relato,

                                     @NotNull
                                     Boolean anonima,

                                     String setorRelacionado,

                                     LocalDateTime dataOcorrencia) {


}
