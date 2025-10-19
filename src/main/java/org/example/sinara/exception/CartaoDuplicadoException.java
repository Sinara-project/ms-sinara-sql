package org.example.sinara.exception;

public class CartaoDuplicadoException extends RuntimeException {
    public CartaoDuplicadoException(String numero) {
        super("O número de cartão " + numero + " já está cadastrado.");
    }
}
