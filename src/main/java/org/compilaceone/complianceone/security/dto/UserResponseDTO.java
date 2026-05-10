package org.compilaceone.complianceone.security.dto;

import org.compilaceone.complianceone.security.enums.Role;

import java.util.UUID;

public record UserResponseDTO(UUID id,
                              String name,
                              String email,
                              Role role) {
}
