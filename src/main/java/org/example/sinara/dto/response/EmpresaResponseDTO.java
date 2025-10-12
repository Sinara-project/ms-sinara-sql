package org.example.sinara.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter

public class EmpresaResponseDTO {
    private Long id;

    private String cnpj;

    private String nome;

    private String senha;

    private String senhaAreaRestrita;

    private String codigo;

    private String imagemUrl;

    private String email;

    private String ramoAtuacao;

    private String telefone;

    private String planoInicial;

    private Long idPlano;
}
