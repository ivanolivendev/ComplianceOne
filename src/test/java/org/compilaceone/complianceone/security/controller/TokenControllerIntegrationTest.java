package org.compilaceone.complianceone.security.controller;

import org.compilaceone.complianceone.security.entity.User;
import org.compilaceone.complianceone.security.enums.Role;
import org.compilaceone.complianceone.security.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class TokenControllerIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("complianceone_test")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.flyway.url", postgres::getJdbcUrl);
        registry.add("spring.flyway.user", postgres::getUsername);
        registry.add("spring.flyway.password", postgres::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        // Insere um usuário de teste válido no banco de dados do Testcontainers
        User user = User.builder()
                .name("Test User")
                .email("test@integration.com")
                .password(passwordEncoder.encode("senha123"))
                .role(Role.ADMIN)
                .active(true)
                .build();
        userRepository.save(user);
    }

    @Test
    @DisplayName("Deve realizar login com sucesso e retornar um JWT para credenciais corretas")
    void deveFazerLoginComSucesso() throws Exception {
        String jsonRequest = """
            {
                "email": "test@integration.com",
                "password": "senha123"
            }
            """;

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.accessToken").exists())
               .andExpect(jsonPath("$.expiresIn").value(86400));
    }

    @Test
    @DisplayName("Deve falhar e retornar 403 para credenciais incorretas")
    void deveFalharParaSenhaIncorreta() throws Exception {
        String jsonRequest = """
            {
                "email": "test@integration.com",
                "password": "senha_errada"
            }
            """;

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
               .andExpect(status().isForbidden()); // Spring retorna 403 por default quando não tratado
    }
}
