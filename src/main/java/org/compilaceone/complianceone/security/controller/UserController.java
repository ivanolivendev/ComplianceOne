package org.compilaceone.complianceone.security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.compilaceone.complianceone.security.dto.UserRequestDTO;
import org.compilaceone.complianceone.security.dto.UserResponseDTO;
import org.compilaceone.complianceone.security.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
    }
}
