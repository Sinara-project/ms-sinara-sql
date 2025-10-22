package org.example.sinara.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.sinara.validation.OnCreate;

import java.time.LocalDate;

@Data
public class CartaoCreditoRequestDTO {

    @NotBlank(message = "O número do cartão não pode estar em branco", groups = OnCreate.class)
    @Size(min = 13, max = 20, message = "O número do cartão deve ter entre 13 e 20 dígitos")
    private String numero;

    @NotBlank(message = "O nome do titular é obrigatório", groups = OnCreate.class)
    @Size(max = 100, message = "O nome do titular pode ter no máximo 100 caracteres")
    private String nomeTitular;

    @NotNull(message = "A data de validade é obrigatória", groups = OnCreate.class)
    @Future(message = "A validade deve ser uma data futura")
    private LocalDate validade;

    @NotBlank(message = "O CVV é obrigatório", groups = OnCreate.class)
    @Pattern(regexp = "\\d{3,4}", message = "O CVV deve ter 3 ou 4 dígitos numéricos")
    private String cvv;

    @NotNull(message = "O ID da empresa é obrigatório", groups = OnCreate.class)
    @Positive(message = "O ID da empresa deve ser um número positivo")
    private Integer idEmpresa;
}
