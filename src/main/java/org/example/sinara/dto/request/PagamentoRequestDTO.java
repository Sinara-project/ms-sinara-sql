package org.example.sinara.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PagamentoRequestDTO {
    @NotNull(message = "O valor é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
//    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 10 dígitos inteiros e 2 decimais")
    private BigDecimal valor;

    @NotNull(message = "A data do pagamento é obrigatória")
    private LocalDateTime data;

    @NotBlank(message = "O status é obrigatório")
    @Size(max = 20, message = "O status pode ter no máximo 20 caracteres")
    private String status;

    @NotNull(message = "O ID do cartão de crédito é obrigatório")
    @Positive(message = "O ID do cartão de crédito deve ser positivo")
    private Long idCartaoCredito;

    @NotNull(message = "O ID da empresa é obrigatório")
    @Positive(message = "O ID da empresa deve ser positivo")
    private Long idEmpresa;
}
