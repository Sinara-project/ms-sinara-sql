package org.example.sinara.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.example.sinara.validation.OnCreate;

@Getter
@Setter
public class OperarioRequestDTO {

    @NotNull(message = "ID da empresa é obrigatório")
    private Integer idEmpresa;

    @Size(max = 255, message = "URL deve ter no máximo 255 caracteres")
    private String url;

    @Size(max = 255, message = "URL deve ter no máximo 255 caracteres")
    private String imagemUrl;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 números")
    private String cpf;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    private String email;

    @NotBlank(message = "Cargo é obrigatório")
    @Size(max = 50, message = "Cargo deve ter no máximo 50 caracteres")
    private String cargo;

    @NotBlank(message = "Setor é obrigatório")
    @Size(min = 2,max = 50, message = "Cargo deve ter no minimo 2 caracteres e no máximo 50")
    private String setor;

    @Min(value = 0, message = "Horas previstas não pode ser negativa")
    @Max(value = 744, message = "Horas previstas não pode ultrapassar 744 (máximo em um mês)")
    private Integer horasPrevistas;

    private Boolean ferias;

    private Boolean ativo;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, max = 50, message = "Senha deve ter entre 8 e 50 caracteres")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$",
            message = "Senha deve conter pelo menos uma letra maiúscula, uma minúscula e um número"
    )
    private String senha;
}
