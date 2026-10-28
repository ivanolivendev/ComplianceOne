package org.compilaceone.complianceone.ocorrencia.repository;

import org.compilaceone.complianceone.ocorrencia.domain.entity.Anexo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface AnexoRepository extends JpaRepository<Anexo, UUID> {
    List<Anexo> findByOcorrenciaIdOrderByDataUploadDesc(UUID ocorrenciaId);
}
