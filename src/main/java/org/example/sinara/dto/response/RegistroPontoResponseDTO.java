package org.example.sinara.dto.response;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegistroPontoResponseDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime horarioEntrada;

    private LocalDateTime horarioSaida;

    private Integer idOperario;

    private Integer idEmpresa;
}
