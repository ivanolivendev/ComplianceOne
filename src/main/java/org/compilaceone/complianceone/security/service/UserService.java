package org.compilaceone.complianceone.security.service;

import lombok.RequiredArgsConstructor;
import org.compilaceone.complianceone.security.dto.UserRequestDTO;
import org.compilaceone.complianceone.security.dto.UserResponseDTO;
import org.compilaceone.complianceone.security.entity.User;
import org.compilaceone.complianceone.security.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDTO create(UserRequestDTO request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado");
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

    @Transactional(readOnly = true)
    public List<UserResponseDTO> list() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional
    public UserResponseDTO setActive(UUID id, boolean active) {
        User user = userRepository.findById(id).orElseThrow(() -> new java.util.NoSuchElementException("Usuário não encontrado"));
        user.setActive(active);
        return toResponse(userRepository.save(user));
    }

    private UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getActive(), user.getCreatedAt());
    }
}
