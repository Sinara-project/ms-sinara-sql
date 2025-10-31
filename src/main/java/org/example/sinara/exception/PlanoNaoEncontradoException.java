package org.example.sinara.exception;

public class PlanoNaoEncontradoException extends RuntimeException {
    public PlanoNaoEncontradoException(Integer idPlano) {
        super("Plano não encontrado");
    }
}