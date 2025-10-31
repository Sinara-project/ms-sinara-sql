package org.example.sinara.exception;

public class EmpresaNaoEncontradaException extends RuntimeException {

    public EmpresaNaoEncontradaException(Integer idEmpresa) {
        super("Empresa com ID " + idEmpresa + " não foi encontrada.");
    }
}
