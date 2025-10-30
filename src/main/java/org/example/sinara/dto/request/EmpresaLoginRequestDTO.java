package org.example.sinara.dto.request;

import lombok.Data;

@Data
public class EmpresaLoginRequestDTO {
    private String cnpj;
    private String senha;
}