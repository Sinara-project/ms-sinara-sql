package org.example.sinara.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.example.sinara.validation.OnCreate;
import org.hibernate.validator.constraints.br.CNPJ;

import java.time.LocalDateTime;

@Getter
@Setter

public class EmpresaRequestDTO {
    @NotBlank(message = "CNPJ é obrigatório", groups = OnCreate.class)
    @CNPJ(message = "CNPJ inválido")
    private String cnpj;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 255, message = "O nome deve ter no minimo 2 caracteres e no máximo 255 caracteres")
    private String nome;

    @Size(min = 8, max = 255, message = "Senha deve ter no mínimo 8 caracteres e no máximo 255 caracteres")
    @NotBlank(message = "senha é obrigatória")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$",
            message = "Senha deve conter pelo menos uma letra maiúscula, uma minúscula e um número"
    )
    private String senha;

    @Size(min = 8, max = 255, message = "Senha da área restrita deve ter no mínimo 8 caracteres e no máximo 255 caracteres")
    @NotBlank(message = "Senha da área restrita é obrigatória")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$",
            message = "Senha da área restrita deve conter pelo menos uma letra maiúscula, uma minúscula e um número"
    )
    private String senhaAreaRestrita;

    @Size(min = 6, max = 50, message = "Código deve ter no mínimo 6 caracteres e no máximo 50 caracteres")
    @NotBlank(message = "Senha da área restrita é obrigatória")
    private String codigo;

    @NotBlank(message = "A url não pode estar em branco", groups = OnCreate.class)
    @Size(max = 255, message = "URL deve ter no máximo 255 caracteres")
    private String imagemUrl;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "O ram de atuação é obrigatória")
    private String ramoAtuacao;

    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    private String telefone;

    @NotBlank(message = "Status da conta é obrigatório", groups = OnCreate.class)
    @Size(max = 100, message = "O plano inicial deve ter no máximo 100 caracteres")
    private String planoInicial;

    private Long idPlano;
}
