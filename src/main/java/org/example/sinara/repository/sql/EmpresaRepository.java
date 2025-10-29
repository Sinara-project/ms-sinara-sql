package org.example.sinara.repository.sql;

import org.example.sinara.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

//    Query
    @Query(value = """
        SELECT 
            e.id AS id,
            e.imagem_url AS imagemUrl,
            e.codigo AS codigo,
            e.cnpj AS cnpj,
            e.nome AS nome,
            e.email AS email,
            e.ramo_atuacao AS ramoAtuacao,
            e.telefone AS telefone
        FROM empresa e
        WHERE e.id = :id
    """, nativeQuery = true)
    Map<String, Object> buscarPerfilPorId(@Param("id") Integer id);

    @Query(value = """
        SELECT 
            e.id AS id,
            e.imagem_url AS imagemUrl,
            e.codigo AS codigo,
            e.nome AS nome,
            e.email AS email,
            e.ramo_atuacao AS ramoAtuacao,
            e.telefone AS telefone
        FROM empresa e
        WHERE e.cnpj = :cnpj
    """, nativeQuery = true)
    Map<String, Object> findIdByCnpj(@Param("cnpj") String cnpj);

    //    Métodos derivados
    boolean existsByCodigo(String codigo);

    boolean existsByCnpj(String cnpj);

    //Procedure
    @Procedure(procedureName = "mudar_para_premium")
    void mudarParaPremium(@Param("p_id_empresa") Integer empresaId,
                          @Param("p_id_cartao") Integer cartaoId);

    //  Function
    @Query(value = "SELECT rebaixar_planos_por_inadimplencia_fn()", nativeQuery = true)
    void rebaixarPlanosPorInadimplencia();
}
