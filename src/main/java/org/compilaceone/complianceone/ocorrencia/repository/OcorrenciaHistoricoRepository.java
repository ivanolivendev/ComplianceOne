package org.compilaceone.complianceone.ocorrencia.repository;

import org.compilaceone.complianceone.ocorrencia.domain.entity.OcorrenciaHistorico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface OcorrenciaHistoricoRepository extends JpaRepository<OcorrenciaHistorico, UUID> {
    List<OcorrenciaHistorico> findByOcorrenciaIdOrderByDataEventoAsc(UUID ocorrenciaId);
}
