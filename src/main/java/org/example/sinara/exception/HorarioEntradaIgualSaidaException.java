package org.example.sinara.exception;

public class HorarioEntradaIgualSaidaException extends RuntimeException {
    public HorarioEntradaIgualSaidaException() {
        super("Horário de entrada e saída não podem ser iguais.");
    }
}
