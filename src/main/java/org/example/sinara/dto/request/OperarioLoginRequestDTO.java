package org.example.sinara.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.sinara.validation.OnCreate;

@Data
public class OperarioLoginRequestDTO {

    @NotBlank(message = "Email é obrigatório", groups = OnCreate.class)
    @Email(message = "Email deve ser válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    private String email;

    @NotBlank(message = "CPF é obrigatório", groups = OnCreate.class)
    @Pattern(regexp = "\\d{11}", message = "CPF deve ter exatamente 11 números")
    private String cpf;

    @NotBlank(message = "Senha é obrigatória", groups = OnCreate.class)
    @Size(min = 8, max = 50, message = "Senha deve ter entre 8 e 50 caracteres")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$",
            message = "Senha deve conter pelo menos uma letra maiúscula, uma minúscula e um número"
    )
    private String senha;

    @NotBlank(message = "Código da empresa é obrigatório", groups = OnCreate.class)
    @Size(min = 6, max = 6, message = "Código da empresa deve ter exatamente 6 caracteres")
    private String codigoEmpresa;
}
