package org.example.sinara.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valor;

    private LocalDateTime data;

    private String status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_empresa", referencedColumnName = "id")
        private Empresa Empresa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cartao_credito", referencedColumnName = "id")
    private CartaoCredito CartaoCredito;
}
