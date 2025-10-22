package org.example.sinara.exception;

public class CnpjDuplicadoException extends RuntimeException {
    public CnpjDuplicadoException(String cnpj) {
        super("CNPJ já cadastrado: " + cnpj);
    }
}
