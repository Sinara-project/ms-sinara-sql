package org.example.sinara.repository.sql;

import org.example.sinara.model.Operario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface OperarioRepository extends JpaRepository<Operario, Integer> {
//    Query
    @Query(value = """
    SELECT 
        o.nome AS nome,
        o.imagem_url AS imagemUrl,
        o.horas_previstas AS horasPrevistas
    FROM operario o
    JOIN empresa e ON o.id_empresa = e.id
    WHERE o.id = :id
""", nativeQuery = true)
    Map<String, Object> buscarPerfilOperarioPorId(@Param("id") Integer id);

    @Query("SELECT o.horasPrevistas FROM Operario o WHERE o.id = :idOperario")
    Integer findHorasPrevistasByOperario(@Param("idOperario") Integer idOperario);

    @Query("SELECT o.id FROM Operario o WHERE o.cpf = :cpf")
    String findIdByCpf(@Param("cpf") String cpf);

    //    Procedure
    @Procedure(procedureName = "atualizar_status_funcionario")
    void atualizarStatus(@Param("p_id_operario") Integer operarioId,
                         @Param("p_ativo") Boolean ativo,
                         @Param("p_ferias") Boolean ferias);

//    Métodos derivados
    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}
