package org.compilaceone.complianceone.security.dto;

public record LoginResponseDTO(String accessToken, Long expiresIn) {
}