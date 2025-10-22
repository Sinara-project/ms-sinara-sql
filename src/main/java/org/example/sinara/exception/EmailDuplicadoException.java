package org.example.sinara.exception;

public class EmailDuplicadoException extends RuntimeException {
    public EmailDuplicadoException(String email) {
        super("O email '" + email + "' já está cadastrado.");
    }
}
