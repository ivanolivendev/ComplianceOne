package org.compilaceone.complianceone.infra.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDTO> handleRuntime(RuntimeException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        
        // Se a mensagem for "não encontrada", retornamos 404
        if (ex.getMessage().toLowerCase().contains("não encontrada")) {
            status = HttpStatus.NOT_FOUND;
        }

        ExceptionDTO error = new ExceptionDTO(status, ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionDTO> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        ExceptionDTO error = new ExceptionDTO(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDTO> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ExceptionDTO error = new ExceptionDTO(HttpStatus.BAD_REQUEST, "Erro de validação: " + message, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
