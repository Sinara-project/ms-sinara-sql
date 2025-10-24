package org.example.sinara.dto.response;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CartaoCreditoResponseDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String numero;

    private String nomeTitular;

    private LocalDate validade;

    private String cvv;

    private Integer idEmpresa;
}
