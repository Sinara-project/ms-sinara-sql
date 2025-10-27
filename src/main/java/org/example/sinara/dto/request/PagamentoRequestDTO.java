package org.example.sinara.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.example.sinara.validation.OnCreate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PagamentoRequestDTO {
    @NotNull(message = "O valor é obrigatório", groups = OnCreate.class)
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
//    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 10 dígitos inteiros e 2 decimais")
    private BigDecimal valor;

    @NotNull(message = "A data do pagamento é obrigatória", groups = OnCreate.class)
    private LocalDateTime dataPagamento;

    @NotBlank(message = "O status é obrigatório", groups = OnCreate.class)
    @Size(max = 20, message = "O status pode ter no máximo 20 caracteres")
    private String status;

    @NotNull(message = "O ID do cartão de crédito é obrigatório", groups = OnCreate.class)
    @Positive(message = "O ID do cartão de crédito deve ser positivo")
    private Integer idCartaoCredito;

    @NotNull(message = "O ID da empresa é obrigatório", groups = OnCreate.class)
    @Positive(message = "O ID da empresa deve ser positivo")
    private Integer idEmpresa;
}
