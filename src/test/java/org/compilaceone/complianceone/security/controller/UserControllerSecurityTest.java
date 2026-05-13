package org.compilaceone.complianceone.security.controller;

import org.compilaceone.complianceone.security.config.SecurityConfig;
import org.compilaceone.complianceone.security.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = true)
@Import({SecurityConfig.class, org.compilaceone.complianceone.security.filter.JwtAuthenticationFilter.class})
@org.springframework.test.context.TestPropertySource(properties = {
        "jwt.public.key=classpath:app.pub",
        "jwt.private.key=classpath:app.key"
})
class UserControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private org.springframework.security.oauth2.jwt.JwtEncoder jwtEncoder;

    @MockitoBean
    private org.springframework.security.core.userdetails.UserDetailsService userDetailsService;

    @Test
    @DisplayName("Acesso anônimo a POST /api/v1/users deve retornar 403 Forbidden")
    void acessoAnonimoDeveSerNegado() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Teste\",\"email\":\"t@t.com\",\"password\":\"123\",\"role\":\"RH\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "RH")
    @DisplayName("Usuário RH não deve ter permissão para criar outros usuários")
    void rhNaoPodeCriarUsuarios() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Teste\",\"email\":\"t@t.com\",\"password\":\"123\",\"role\":\"RH\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "DIRETORIA")
    @DisplayName("Usuário DIRETORIA deve ter permissão para criar usuários")
    void diretoriaPodeCriarUsuarios() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Teste\",\"email\":\"t@t.com\",\"password\":\"123\",\"role\":\"RH\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Usuário ADMIN deve ter permissão para criar usuários")
    void adminPodeCriarUsuarios() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Teste\",\"email\":\"t@t.com\",\"password\":\"123\",\"role\":\"RH\"}"))
                .andExpect(status().isCreated());
    }
}
