package org.compilaceone.complianceone.ocorrencia.dto;

import org.compilaceone.complianceone.ocorrencia.domain.entity.Anexo;
import java.time.LocalDateTime;
import java.util.UUID;

public record AnexoResponse(UUID id, String nomeArquivo, String nomeOriginal, Long tamanho,
                            String tipoArquivo, String hashArquivo, LocalDateTime dataUpload) {
    public static AnexoResponse from(Anexo a) { return new AnexoResponse(a.getId(), a.getNomeArquivo(), a.getNomeOriginal(), a.getTamanho(), a.getTipoArquivo(), a.getHashArquivo(), a.getDataUpload()); }
}
