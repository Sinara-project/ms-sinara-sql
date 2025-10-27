package org.example.sinara.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal valor;

    private LocalDateTime dataPagamento;

    private String status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_empresa", referencedColumnName = "id")
        private Empresa Empresa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cartao_credito", referencedColumnName = "id")
    private CartaoCredito CartaoCredito;
}
