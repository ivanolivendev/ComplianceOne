package org.compilaceone.complianceone.security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.compilaceone.complianceone.security.dto.UserRequestDTO;
import org.compilaceone.complianceone.security.dto.UserResponseDTO;
import org.compilaceone.complianceone.security.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> list() { return ResponseEntity.ok(userService.list()); }

    @PatchMapping("/{id}/active")
    public ResponseEntity<UserResponseDTO> setActive(@PathVariable UUID id, @RequestParam boolean active) {
        return ResponseEntity.ok(userService.setActive(id, active));
    }
}
