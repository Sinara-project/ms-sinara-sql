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
    @Size(max = 255, message = "O nome deve ter no máximo 255 caracteres")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email corporativo inválido")
    private String emailCorporativo;

    @NotBlank(message = "O ram de atuação é obrigatória")
    private String ramoAtuacao;

    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    private String telefone;

    @NotBlank(message = "Status da conta é obrigatório", groups = OnCreate.class)
    @Size(max = 100, message = "O plano inicial deve ter no máximo 100 caracteres")
    private String planoInicial;
}
