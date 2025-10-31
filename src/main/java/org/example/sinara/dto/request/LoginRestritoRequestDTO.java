package org.example.sinara.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRestritoRequestDTO {
    @NotNull(message = "O id do plano é obrigatório")
    @Min(value = 1, message = "O id do plano deve ser maior que 0")
    private Integer idEmpresa;

    @Size(min = 8, max = 255, message = "Senha da área restrita deve ter no mínimo 8 caracteres e no máximo 255 caracteres")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$",
            message = "Senha da área restrita deve conter pelo menos uma letra maiúscula, uma minúscula e um número"
    )
    private String senha;
}
