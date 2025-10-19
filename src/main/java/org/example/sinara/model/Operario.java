package org.example.sinara.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Operario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_empresa", referencedColumnName = "id")
    private Empresa idEmpresa;
}
