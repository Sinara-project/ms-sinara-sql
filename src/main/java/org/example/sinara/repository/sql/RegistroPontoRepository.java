package org.example.sinara.repository.sql;

import org.example.sinara.model.Empresa;
import org.example.sinara.model.RegistroPonto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RegistroPontoRepository extends JpaRepository<RegistroPonto, Integer> {

//    Métodos derivados
    Optional<RegistroPonto> findTopByOperario_IdOrderByHorarioEntradaDesc(Integer idOperario);

//    Query
    @Query(value = """
            SELECT 
                DATE(r.horario_saida) AS dia,
                r.horario_entrada AS horarioEntrada,
                r.horario_saida AS horarioSaida
            FROM registro_ponto r
            WHERE r.id_operario = :id
            ORDER BY r.horario_saida DESC
            LIMIT 1
        """, nativeQuery = true)
    Map<String, Object> buscarUltimoTurnoOperario(@Param("id") Integer id);

    @Query("""
    SELECT (COUNT(r.horarioEntrada) + COUNT(r.horarioSaida))
    FROM RegistroPonto r
    WHERE r.operario.id = :idOperario
""")
    int contarPontosPorOperario(@Param("idOperario") Integer idOperario);

    @Query("""
    SELECT r 
    FROM RegistroPonto r 
    WHERE r.operario.id = :idOperario
    AND MONTH(r.horarioEntrada) = MONTH(CURRENT_DATE)
    AND YEAR(r.horarioEntrada) = YEAR(CURRENT_DATE)
""")
    List<RegistroPonto> buscarRegistrosDoMesAtual(@Param("idOperario") Integer idOperario);

}
