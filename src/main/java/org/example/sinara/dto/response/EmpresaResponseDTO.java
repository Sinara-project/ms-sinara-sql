package org.example.sinara.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter

public class EmpresaResponseDTO {
    private String cnpj;

    private String nome;

    private String emailCorporativo;

    private String ramoAtuacao;

    private String telefone;

    private String planoInicial;
}
