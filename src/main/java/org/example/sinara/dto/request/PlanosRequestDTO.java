package org.example.sinara.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.sinara.validation.OnCreate;

@Getter
@Setter
public class PlanosRequestDTO {
    @NotBlank(message = "Nome é obrigatório", groups = OnCreate.class)
    @Size(min = 2, max = 255, message = "O nome deve ter no minimo 2 caracteres e no máximo 255 caracteres")
    private String nome;

    @DecimalMin(value = "0.0", inclusive = false, message = "Preço mensal deve ser maior que zero")
    private Double precoMensal;

    @DecimalMin(value = "0.0", inclusive = false, message = "Preço anual deve ser maior que zero")
    private Double precoAnual;

    @NotBlank(message = "Recursos são obrigatórios", groups = OnCreate.class)
    @Size(min = 5, max = 1000, message = "Recursos devem ter entre 5 e 1000 caracteres")
    private String recursos;
}
