package org.example.sinara.exception;

public class CpfDuplicadoException extends RuntimeException {
    public CpfDuplicadoException(String cpf) {
        super("Já existe um operário cadastrado com o CPF: " + cpf);
    }}
