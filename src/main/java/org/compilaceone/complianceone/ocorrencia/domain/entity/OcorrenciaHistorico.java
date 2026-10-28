package org.compilaceone.complianceone.ocorrencia.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.compilaceone.complianceone.ocorrencia.domain.enums.StatusOcorrencia;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ocorrencia_historico")
@Getter @NoArgsConstructor @AllArgsConstructor @Builder
public class OcorrenciaHistorico {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ocorrencia_id", nullable = false, updatable = false)
    private Ocorrencia ocorrencia;

    @Enumerated(EnumType.STRING)
    private StatusOcorrencia statusAnterior;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOcorrencia statusNovo;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataEvento;
}
