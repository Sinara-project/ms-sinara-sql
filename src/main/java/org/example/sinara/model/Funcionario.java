package org.example.sinara.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {
    private String idEmpresa;

    private int idPermissoes;

    private String cnpj;

    private String nome;

    private String email;

    private String cargo;

    private double horasTrabalhadas;

    private String expediente;

    private boolean ferias;

}
