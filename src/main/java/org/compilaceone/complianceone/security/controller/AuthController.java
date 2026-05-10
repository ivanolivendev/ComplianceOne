package org.compilaceone.complianceone.security.controller;


import lombok.RequiredArgsConstructor;
import org.compilaceone.complianceone.security.dto.LoginRequestDTO;
import org.compilaceone.complianceone.security.dto.LoginResponseDTO;
import org.compilaceone.complianceone.security.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO request
    ) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}
