package org.example.sinara.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class RegistroPontoRequestDTO {

    @FutureOrPresent(message = "O horário não pode estar no futuro")
    @NotNull(message = "O horário de entrada não pode ser nulo")
    private LocalDateTime horarioEntrada;

    @FutureOrPresent(message = "O horário não pode estar no futuro")
    @NotNull(message = "O horário de saída não pode ser nulo")
    private LocalDateTime horarioSaida;

    @Positive(message = "O ID do operário deve ser positivo")
    @NotNull(message = "O ID do operário é obrigatório")
    private Integer idOperario;

    @Positive(message = "O ID da empresa deve ser positivo")
    @NotNull(message = "O ID da empresa é obrigatório")
    private Integer idEmpresa;
}
