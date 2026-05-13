package org.compilaceone.complianceone.security.service;

import org.compilaceone.complianceone.security.dto.LoginRequestDTO;
import org.compilaceone.complianceone.security.dto.LoginResponseDTO;
import org.compilaceone.complianceone.security.entity.User;
import org.compilaceone.complianceone.security.enums.Role;
import org.compilaceone.complianceone.security.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtEncoder jwtEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("Deve realizar login com sucesso e retornar token")
    void deveRealizarLoginComSucesso() {
        // Arrange
        LoginRequestDTO request = new LoginRequestDTO("admin@complianceone.com", "senha123");
        User user = User.builder()
                .id(UUID.randomUUID())
                .email(request.email())
                .role(Role.ADMIN)
                .build();

        Jwt jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn("token-fake-123");
        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);
        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(user));

        // Act
        LoginResponseDTO response = authService.login(request);

        // Assert
        assertNotNull(response);
        assertEquals("token-fake-123", response.accessToken());
        verify(authenticationManager, times(1)).authenticate(any());
    }

    @Test
    @DisplayName("Deve lançar BadCredentialsException ao falhar na autenticação")
    void deveLancarExcecaoAoFalharLogin() {
        // Arrange
        LoginRequestDTO request = new LoginRequestDTO("errado@teste.com", "123");
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Falha"));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> authService.login(request));
    }
}
