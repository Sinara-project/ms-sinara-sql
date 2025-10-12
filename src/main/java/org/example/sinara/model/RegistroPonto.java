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
public class RegistroPonto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime horarioEntrada;

    private LocalDateTime horarioSaida;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_operario", referencedColumnName = "id")
    private Operario idOperario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_empresa", referencedColumnName = "id")
    private Empresa idEmpresa;
}
