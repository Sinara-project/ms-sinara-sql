package org.example.sinara.repository.sql;

import org.example.sinara.model.CartaoCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface CartaoCreditoRepository extends JpaRepository<CartaoCredito, Integer> {
//    Metodo derivado
    boolean existsByNumero(String numero);

//    Function
    @Query(value = "SELECT validar_cartao_credito(:numero, :validade)", nativeQuery = true)
    Boolean validarCartao(@Param("numero") String numero, @Param("validade") LocalDate validade);
}
