package org.example.sinara.repository.sql;

import org.example.sinara.model.Operario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Query(value = """
    SELECT 
        o.id AS id,
        o.nome AS nome,
        o.email AS email,
        o.cargo AS cargo,
        o.setor AS setor,
        o.imagem_url AS imagemUrl,
        o.ativo AS ativo,
        o.ferias AS ferias,
        o.horas_previstas AS horasPrevistas
    FROM operario o
    WHERE o.id_empresa = :idEmpresa
""", nativeQuery = true)
    List<Map<String, Object>> buscarOperariosPorIdEmpresa(@Param("idEmpresa") Integer idEmpresa);

    @Query("""
    SELECT o FROM Operario o
    JOIN o.idEmpresa e
    WHERE (o.email = :email OR o.cpf = :cpf)
    AND e.codigo = :codigoEmpresa
    """)
    Optional<Operario> findByLogin(
            @Param("email") String email,
            @Param("cpf") String cpf,
            @Param("codigoEmpresa") String codigoEmpresa
    );


    //    Procedure
    @Procedure(procedureName = "atualizar_status_funcionario")
    void atualizarStatus(@Param("p_id_operario") Integer operarioId,
                         @Param("p_ativo") Boolean ativo,
                         @Param("p_ferias") Boolean ferias);

//    Métodos derivados
    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}
