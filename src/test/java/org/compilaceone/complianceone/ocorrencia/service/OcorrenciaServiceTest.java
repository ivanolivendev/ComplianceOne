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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
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
    @DisplayName("Deve criar uma ocorrência com protocolo NR1- e status RECEBIDA")
    void deveCriarOcorrenciaComSucesso() {
        CriarOcorrenciaRequest request = new CriarOcorrenciaRequest(
                TipoOcorrencia.ASSEDIO_MORAL,
                "Relato de teste com mais de dez caracteres",
                true,
                "TI",
                LocalDateTime.now()
        );

        Ocorrencia savedOcorrencia = Ocorrencia.builder()
                .id(UUID.randomUUID())
                .protocolo("NR1-ABC12345")
                .tipo(request.tipo())
                .relato(request.relato())
                .anonima(request.anonima())
                .status(StatusOcorrencia.RECEBIDA)
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();

        when(repository.save(any(Ocorrencia.class))).thenReturn(savedOcorrencia);

        OcorrenciaResponse response = service.criar(request);

        assertNotNull(response);
        assertTrue(response.protocolo().startsWith("NR1-"));
        assertEquals(StatusOcorrencia.RECEBIDA, response.status());
        assertEquals(true, response.anonima());
        verify(repository, times(1)).save(any(Ocorrencia.class));
    }

    @Test
    @DisplayName("Deve listar ocorrências de forma paginada")
    void deveListarOcorrenciasPaginadas() {
        Pageable pageable = PageRequest.of(0, 10);
        Ocorrencia ocorrencia = Ocorrencia.builder().id(UUID.randomUUID()).build();
        Page<Ocorrencia> page = new PageImpl<>(List.of(ocorrencia));

        when(repository.findAll(pageable)).thenReturn(page);

        Page<OcorrenciaResponse> result = service.listarTodas(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Deve realizar o soft delete (desativar) de uma ocorrência")
    void deveRealizarSoftDelete() {
        UUID id = UUID.randomUUID();
        Ocorrencia ocorrencia = Ocorrencia.builder().id(id).ativo(true).build();

        when(repository.findById(id)).thenReturn(Optional.of(ocorrencia));

        service.deletar(id);

        assertFalse(ocorrencia.getAtivo());
        verify(repository, times(1)).save(ocorrencia);
    }

    @Test
    @DisplayName("Deve buscar ocorrência por protocolo com sucesso")
    void deveBuscarPorProtocolo() {
        String protocolo = "NR1-TESTE";
        Ocorrencia ocorrencia = Ocorrencia.builder()
                .protocolo(protocolo)
                .status(StatusOcorrencia.RECEBIDA)
                .build();

        when(repository.findByProtocolo(protocolo)).thenReturn(Optional.of(ocorrencia));

        OcorrenciaResponse response = service.buscarPorProtocolo(protocolo);

        assertNotNull(response);
        assertEquals(protocolo, response.protocolo());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar protocolo inexistente")
    void deveLancarExcecaoProtocoloInexistente() {
        String protocolo = "NR1-FALSO";
        when(repository.findByProtocolo(protocolo)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.buscarPorProtocolo(protocolo));
    }
}
