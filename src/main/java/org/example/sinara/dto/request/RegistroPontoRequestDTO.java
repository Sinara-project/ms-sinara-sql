package org.example.sinara.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.example.sinara.validation.OnCreate;

import java.time.LocalDateTime;

@Getter
@Setter

public class RegistroPontoRequestDTO {

    @PastOrPresent(message = "O horário não pode ser no futuro")
//    @NotNull(message = "O horário de entrada não pode ser nulo", groups = OnCreate.class)
    private LocalDateTime horarioEntrada;

    @PastOrPresent(message = "O horário não pode ser no futuro")
//    @NotNull(message = "O horário de saída não pode ser nulo", groups = OnCreate.class)
    private LocalDateTime horarioSaida;

//    @Positive(message = "Banco de horas deve ser positivo")
//    @NotNull(message = "Banco de horas não pode ser nulo", groups = OnCreate.class)
//    private Integer bancoHoras;

    @Positive(message = "O ID do operário deve ser positivo")
    @NotNull(message = "O ID do operário é obrigatório", groups = OnCreate.class)
    private Integer idOperario;

    @Positive(message = "O ID da empresa deve ser positivo")
    @NotNull(message = "O ID da empresa é obrigatório", groups = OnCreate.class)
    private Integer idEmpresa;
}
