package org.example.sinara.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RegistroPonto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime horarioEntrada;

    private LocalDateTime horarioSaida;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_operario", referencedColumnName = "id")
    private Operario operario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_empresa", referencedColumnName = "id")
    private Empresa empresa;
}
