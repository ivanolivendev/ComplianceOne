package org.compilaceone.complianceone.infra.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ExceptionDTO(
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    String path
) {
    public ExceptionDTO(HttpStatus status, String message, String path) {
        this(LocalDateTime.now(), status.value(), status.getReasonPhrase(), message, path);
    }
}
