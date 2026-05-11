package org.compilaceone.complianceone.security.controller;

import lombok.RequiredArgsConstructor;
import org.compilaceone.complianceone.security.entity.User;
import org.compilaceone.complianceone.security.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/debug")
@RequiredArgsConstructor
public class DebugAuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/users/{email}")
    public ResponseEntity<?> findUser(@PathVariable String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            return ResponseEntity.ok("❌ Usuário não encontrado: " + email);
        }

        User u = user.get();
        return ResponseEntity.ok(new Object() {
            public String id = u.getId().toString();
            public String name = u.getName();
            public String email = u.getEmail();
            public String role = u.getRole().name();
            public Boolean active = u.getActive();
            public String passwordHash = u.getPassword();
        });
    }

    @PostMapping("/test-password")
    public ResponseEntity<?> testPassword(@RequestParam String email, @RequestParam String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            return ResponseEntity.ok("❌ Usuário não encontrado");
        }

        User u = user.get();
        boolean matches = passwordEncoder.matches(password, u.getPassword());

        return ResponseEntity.ok(new Object() {
            public String result = matches ? "✅ Senha CORRETA" : "❌ Senha INCORRETA";
            public String providedPassword = password;
            public String storedHash = u.getPassword();
        });
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestParam String email, @RequestParam String password, @RequestParam String name) {
        User user = User.builder()
                .email(email)
                .name(name)
                .password(passwordEncoder.encode(password))
                .role(org.compilaceone.complianceone.security.enums.Role.ADMIN)
                .active(true)
                .build();

        User saved = userRepository.save(user);

        return ResponseEntity.ok("✅ Usuário criado: " + saved.getEmail());
    }
}