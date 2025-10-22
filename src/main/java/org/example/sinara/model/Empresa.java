package org.example.sinara.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String cnpj;

    private String nome;

    private String senha;

    private String senhaAreaRestrita;

    @Column(unique = true, nullable = false)
    private String codigo;

    private String imagemUrl;

    private String email;

    private String ramoAtuacao;

    private String telefone;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_plano", referencedColumnName = "id")
    private Planos idPlano;
}
