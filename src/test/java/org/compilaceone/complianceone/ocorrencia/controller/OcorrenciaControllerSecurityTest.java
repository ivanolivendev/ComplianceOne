package org.compilaceone.complianceone.ocorrencia.controller;

import org.compilaceone.complianceone.ocorrencia.service.OcorrenciaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.compilaceone.complianceone.security.config.SecurityConfig;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OcorrenciaController.class)
@AutoConfigureMockMvc(addFilters = true)
@Import(SecurityConfig.class)
class OcorrenciaControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OcorrenciaService ocorrenciaService;

    @MockBean
    private JwtDecoder jwtDecoder;

    @MockBean
    private UserDetailsService userDetailsService;

    // Removemos a injeção do SecurityConfig pois o Spring Security Test já simula a segurança em WebMvcTest com os filtros adequados.
    // Em alguns casos pode ser necessário fazer @Import do SecurityConfig e mockar o JwtDecoder,
    // mas a anotação @WithMockUser costuma bypassar a necessidade do token de fato.

    @Test
    @DisplayName("Acesso anônimo (sem token) a rota protegida deve retornar 403 Forbidden")
    void naoDeveAcessarOcorrenciasSemAutenticacao() throws Exception {
        mockMvc.perform(get("/api/v1/ocorrencias"))
               .andExpect(status().isForbidden()); // O Spring Boot sem token retorna 403 quando não há entry point customizado
    }

    @Test
    @WithMockUser(roles = "TRIAGEM")
    @DisplayName("Usuário com cargo TRIAGEM tentando acessar GET /ocorrencias deve receber 403 Forbidden")
    void triagemNaoPodeAcessarListaOcorrencias() throws Exception {
        // A listagem exige "RH", "COMPLIANCE" ou "DIRETORIA"
        mockMvc.perform(get("/api/v1/ocorrencias"))
               .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "RH")
    @DisplayName("Usuário com cargo RH tentando acessar GET /ocorrencias deve receber 200 OK")
    void rhPodeAcessarListaOcorrencias() throws Exception {
        mockMvc.perform(get("/api/v1/ocorrencias"))
               .andExpect(status().isOk());
    }
}
