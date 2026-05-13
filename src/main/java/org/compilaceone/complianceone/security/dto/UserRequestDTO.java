package org.compilaceone.complianceone.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.compilaceone.complianceone.security.enums.Role;

public record UserRequestDTO(
    @NotBlank String name,
    @NotBlank @Email String email,
    @NotBlank String password,
    @NotNull Role role
) {}
