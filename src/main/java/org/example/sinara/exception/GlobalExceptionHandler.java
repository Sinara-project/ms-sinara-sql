package org.example.sinara.exception;

import org.example.sinara.exception.CartaoDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CartaoDuplicadoException.class)
    public ResponseEntity<?> handleCartaoDuplicado(CartaoDuplicadoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of(
                        "erro", ex.getMessage(),
                        "status", 409,
                        "timestamp", LocalDateTime.now()
                )
        );
    }
}
