package org.compilaceone.complianceone.ocorrencia.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "anexos")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Anexo {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ocorrencia_id", nullable = false)
    private Ocorrencia ocorrencia;
    @Column(name = "nome_arquivo", nullable = false) private String nomeArquivo;
    @Column(name = "nome_original", nullable = false) private String nomeOriginal;
    @Column(nullable = false) private Long tamanho;
    @Column(name = "tipo_arquivo", nullable = false) private String tipoArquivo;
    @Column(name = "hash_arquivo", nullable = false, length = 64) private String hashArquivo;
    @Column(name = "storage_path", nullable = false) private String storagePath;
    @Column(name = "data_upload", nullable = false) private LocalDateTime dataUpload;
    @PrePersist void prePersist() { if (dataUpload == null) dataUpload = LocalDateTime.now(); }
}
