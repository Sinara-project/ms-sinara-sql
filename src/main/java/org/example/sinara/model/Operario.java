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
public class Operario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int idEmpresa;

    private String url;

    private String imagemUrl;

    private String cpf;

    private String nome;

    private String email;

    private String cargo;

    private String setor;

    private Integer horasPrevistas;

    private Boolean ferias;

    private Boolean ativo;

    private String senha;
}
