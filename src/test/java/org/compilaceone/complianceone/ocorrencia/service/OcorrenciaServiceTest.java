package org.compilaceone.complianceone.ocorrencia.service;

import org.compilaceone.complianceone.ocorrencia.domain.entity.Ocorrencia;
import org.compilaceone.complianceone.ocorrencia.domain.enums.StatusOcorrencia;
import org.compilaceone.complianceone.ocorrencia.domain.enums.TipoOcorrencia;
import org.compilaceone.complianceone.ocorrencia.dto.CriarOcorrenciaRequest;
import org.compilaceone.complianceone.ocorrencia.dto.OcorrenciaResponse;
import org.compilaceone.complianceone.ocorrencia.repository.OcorrenciaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OcorrenciaServiceTest {

    @Mock
    private OcorrenciaRepository repository;

    @InjectMocks
    private OcorrenciaService service;

    @Test
    @DisplayName("Deve criar uma ocorrência com status RECEBIDA e gerar um protocolo NR1-")
    void deveCriarOcorrenciaComSucesso() {
        // Arrange
        CriarOcorrenciaRequest request = new CriarOcorrenciaRequest(
                TipoOcorrencia.ASSEDIO_MORAL,
                "Relato detalhado de assédio no setor financeiro",
                true,
                "Financeiro",
                LocalDateTime.now()
        );

        Ocorrencia savedOcorrencia = Ocorrencia.builder()
                .id(UUID.randomUUID())
                .protocolo("NR1-12345678")
                .status(StatusOcorrencia.RECEBIDA)
                .dataCriacao(LocalDateTime.now())
                .build();

        when(repository.save(any(Ocorrencia.class))).thenAnswer(invocation -> {
            Ocorrencia o = invocation.getArgument(0);
            o.setId(UUID.randomUUID());
            return o;
        });

        // Act
        OcorrenciaResponse response = service.criar(request);

        // Assert
        assertNotNull(response);
        assertTrue(response.protocolo().startsWith("NR1-"));
        assertEquals(StatusOcorrencia.RECEBIDA, response.status());
        verify(repository, times(1)).save(any(Ocorrencia.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar buscar uma ocorrência por ID que não existe")
    void deveLancarExcecaoAoBuscarOcorrenciaInexistente() {
        // Arrange
        UUID idInexistente = UUID.randomUUID();
        when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.buscarPorId(idInexistente);
        });

        assertEquals("Ocorrência não encontrada", exception.getMessage());
        verify(repository, times(1)).findById(idInexistente);
    }
}
