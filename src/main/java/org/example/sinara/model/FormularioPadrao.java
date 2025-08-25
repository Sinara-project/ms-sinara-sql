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
public class FormularioPadrao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String idFuncionario;

    private String idEmpresa;

    private double tubidezAguaBruta;

    private double tubidezAguaTratada;

    private double phAguaBruta;

    private double phAguaTratada;

    private String corAguaBruta;

    private String corAguaTratada;

    private double nitrato;

    private double fluoreto;

    private double cloroResidual;
}
