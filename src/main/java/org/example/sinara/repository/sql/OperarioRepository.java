package org.example.sinara.repository.sql;

import org.example.sinara.model.Operario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface OperarioRepository extends JpaRepository<Operario, Long> {
//    Operario findByHorasPrevistas(Long id);

    @Query(value = """
    SELECT 
        e.nome AS nome,
        e.imagem_url AS imagemUrl,
        o.horas_previstas AS horasPrevistas
    FROM operario o
    JOIN empresa e ON o.id_empresa = e.id
    WHERE o.id = :id
""", nativeQuery = true)
    Map<String, Object> buscarPerfilOperarioPorId(@Param("id") Long id);

    @Procedure(procedureName = "atualizar_status_funcionario")
    void atualizarStatus(@Param("p_id_operario") Integer operarioId,
                         @Param("p_ativo") Boolean ativo,
                         @Param("p_ferias") Boolean ferias);
}
