package org.compilaceone.complianceone.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.compilaceone.complianceone.security.enums.Role;

public record CreateUserDTO(

        @NotBlank
        @Size(min = 3, max = 150)
        String name,

        @Email
        @NotBlank
        String email,

        @NotBlank
        @Size(min = 6, max = 255)
        String password,

        @NotNull
        Role role

) {
}