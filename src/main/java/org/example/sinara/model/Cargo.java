package org.example.sinara.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cargos")
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
}

