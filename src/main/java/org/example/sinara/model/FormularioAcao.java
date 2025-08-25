package org.example.sinara.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FormularioAcao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titulo;

    private String text;

    private boolean utilizouIa;

    private double turbidezAgua;

    private double phAgua;

    private String corAgua;

    private double nitrato;

    private double fluoreto;

    private double cloroResidual;

    private String solucao;

    private String idFuncionario;
}
