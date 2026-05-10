package org.compilaceone.complianceone.ocorrencia.repository;

import org.compilaceone.complianceone.ocorrencia.domain.entity.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia,UUID> {


}
