package org.compilaceone.complianceone.ocorrencia.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.compilaceone.complianceone.ocorrencia.domain.enums.StatusOcorrencia;
import org.compilaceone.complianceone.ocorrencia.domain.enums.TipoOcorrencia;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.UUID;

    @Entity
    @Table(name = "ocorrencias")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @SQLRestriction("ativo = true")
    public class Ocorrencia {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        @Column(nullable = false, unique = true)
        private String protocolo;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private TipoOcorrencia tipo;

        @Column(columnDefinition = "TEXT", nullable = false)
        private String relato;

        @Column(nullable = false)
        private Boolean anonima;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private StatusOcorrencia status;

        private String setorRelacionado;

        @Column(columnDefinition = "TEXT")
        private String observacao;

        @Column(nullable = false)
        private Boolean ativo;

        private LocalDateTime dataOcorrencia;

        @CreationTimestamp
        private LocalDateTime dataCriacao;

        @PrePersist
        public void prePersist() {
            if (this.ativo == null) {
                this.ativo = true;
            }
        }
    }

