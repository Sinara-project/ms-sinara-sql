package org.example.sinara.exception;

public class PlanoDuplicadoException extends RuntimeException {
    public PlanoDuplicadoException(String nome) {
        super("Já existe um plano com o nome: " + nome);
    }
}
