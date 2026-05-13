package org.compilaceone.complianceone.ocorrencia.controller;

import org.compilaceone.complianceone.ocorrencia.service.OcorrenciaService;
import org.compilaceone.complianceone.security.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OcorrenciaController.class)
@AutoConfigureMockMvc(addFilters = true)
@Import({SecurityConfig.class, org.compilaceone.complianceone.security.filter.JwtAuthenticationFilter.class})
@org.springframework.test.context.TestPropertySource(properties = {
        "jwt.public.key=classpath:app.pub",
        "jwt.private.key=classpath:app.key"
})
class OcorrenciaControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OcorrenciaService ocorrenciaService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private org.springframework.security.oauth2.jwt.JwtEncoder jwtEncoder;

    @MockitoBean
    private org.springframework.security.core.userdetails.UserDetailsService userDetailsService;

    @Test
    @DisplayName("Acesso anônimo a GET /api/v1/ocorrencias deve retornar 403 Forbidden")
    void acessoAnonimoDeveSerNegado() throws Exception {
        mockMvc.perform(get("/api/v1/ocorrencias"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "COMUM")
    @DisplayName("Usuário sem permissão (COMUM) deve retornar 403 Forbidden ao listar")
    void usuarioSemPermissaoDeveSerNegado() throws Exception {
        mockMvc.perform(get("/api/v1/ocorrencias"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Usuário ADMIN deve ter acesso a GET /api/v1/ocorrencias")
    void adminDeveTerAcesso() throws Exception {
        mockMvc.perform(get("/api/v1/ocorrencias"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "RH")
    @DisplayName("Usuário RH deve ter acesso a GET /api/v1/ocorrencias")
    void rhDeveTerAcesso() throws Exception {
        mockMvc.perform(get("/api/v1/ocorrencias"))
                .andExpect(status().isOk());
    }
}
