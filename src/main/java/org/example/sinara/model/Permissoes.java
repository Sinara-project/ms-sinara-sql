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
public class Permissoes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPermissoes;

    private boolean acessoAdm;

    private boolean criarFormularios;

    private boolean dashboardsArea;

}
