package org.compilaceone.complianceone.security.service;


import lombok.RequiredArgsConstructor;
import org.compilaceone.complianceone.security.dto.LoginRequestDTO;
import org.compilaceone.complianceone.security.dto.LoginResponseDTO;
import org.compilaceone.complianceone.security.entity.User;
import org.compilaceone.complianceone.security.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow();

        boolean passwordMatches = passwordEncoder.matches(
                request.password(),
                user.getPassword()
        );

        if (!passwordMatches) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponseDTO(token);
    }
}
