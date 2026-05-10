package org.compilaceone.complianceone.security.dto;

import org.compilaceone.complianceone.security.enums.Role;

public record CreateUserDTO(String name,
                            String email,
                            String password,
                            Role role) {
}
