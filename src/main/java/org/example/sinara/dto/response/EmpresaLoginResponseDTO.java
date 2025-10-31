package org.example.sinara.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmpresaLoginResponseDTO {
    private Integer id;

    private String cnpj;

    private String nome;

    private String codigo;

    private String email;

    private String ramoAtuacao;

    private String imagemUrl;
}