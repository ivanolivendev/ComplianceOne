package org.compilaceone.complianceone.security.service;

import lombok.RequiredArgsConstructor;
import org.compilaceone.complianceone.security.dto.UserRequestDTO;
import org.compilaceone.complianceone.security.dto.UserResponseDTO;
import org.compilaceone.complianceone.security.entity.User;
import org.compilaceone.complianceone.security.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDTO create(UserRequestDTO request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .active(true)
                .build();

        userRepository.save(user);

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getActive(),
                user.getCreatedAt()
        );
    }
}
