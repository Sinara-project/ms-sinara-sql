package org.example.sinara.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.sinara.validation.OnCreate;

@Data
public class EmpresaRequestDTO {

    @NotBlank(message = "CNPJ é obrigatório", groups = OnCreate.class)
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve ter 14 números")
    private String cnpj;

    @NotBlank(message = "Nome é obrigatório", groups = OnCreate.class)
    @Size(min = 2, max = 255, message = "O nome deve ter no minimo 2 caracteres e no máximo 255 caracteres")
    private String nome;

    @Size(min = 8, max = 255, message = "Senha deve ter no mínimo 8 caracteres e no máximo 255 caracteres")
    @NotBlank(message = "senha é obrigatória", groups = OnCreate.class)
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$",
            message = "Senha deve conter pelo menos uma letra maiúscula, uma minúscula e um número"
    )
    private String senha;

    @Size(min = 8, max = 255, message = "Senha da área restrita deve ter no mínimo 8 caracteres e no máximo 255 caracteres")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$",
            message = "Senha da área restrita deve conter pelo menos uma letra maiúscula, uma minúscula e um número"
    )
    private String senhaAreaRestrita;

    @NotBlank(message = "A url não pode estar em branco", groups = OnCreate.class)
    @Size(max = 255, message = "URL deve ter no máximo 255 caracteres")
    private String imagemUrl;

    @NotBlank(message = "Email é obrigatório", groups = OnCreate.class)
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "O ram de atuação é obrigatória", groups = OnCreate.class)
    private String ramoAtuacao;

    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    private String telefone;

    @NotNull(message = "O id do plano é obrigatório")
    @Min(value = 1, message = "O id do plano deve ser maior que 0")
    private Integer idPlano;
}
