package org.example.sinara.dto.response;

import lombok.Data;

@Data
public class EmpresaResponseDTO {
    private Integer id;

    private String cnpj;

    private String nome;

    private String senha;

    private String senhaAreaRestrita;

    private String codigo;

    private String imagemUrl;

    private String email;

    private String ramoAtuacao;

    private String telefone;

    private Integer idPlano;
}
