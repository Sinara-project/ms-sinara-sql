package org.example.sinara.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "acesso_diario_usuarios")
public class AcessoDiarioUsuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate data;

    private String atividades;

    @Column(name = "id_operario", nullable = false)
    private Integer idOperario;

    @Column(name = "id_empresa", nullable = false)
    private Integer idEmpresa;
}
