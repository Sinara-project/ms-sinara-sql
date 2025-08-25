package org.example.sinara.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {
    private String cnpj;

    private String nome;

    private String email;

    private String senha;

    private String telefone;

    private String codigoRegistro;
}
